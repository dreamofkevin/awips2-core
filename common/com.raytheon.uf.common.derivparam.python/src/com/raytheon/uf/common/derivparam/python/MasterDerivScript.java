/**
 * This software was developed and / or modified by Raytheon Company,
 * pursuant to Contract DG133W-05-CQ-1067 with the US Government.
 * 
 * U.S. EXPORT CONTROLLED TECHNICAL DATA
 * This software product contains export-restricted data whose
 * export/transfer/disclosure is restricted by U.S. law. Dissemination
 * to non-U.S. persons whether in the United States or abroad requires
 * an export license or other authorization.
 * 
 * Contractor Name:        Raytheon Company
 * Contractor Address:     6825 Pine Street, Suite 340
 *                         Mail Stop B8
 *                         Omaha, NE 68106
 *                         402.291.0100
 * 
 * See the AWIPS II Master Rights File ("Master Rights File.pdf") for
 * further licensing information.
 **/
package com.raytheon.uf.common.derivparam.python;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jep.JepException;
import jep.NDArray;

import com.raytheon.uf.common.datastorage.DataStoreFactory;
import com.raytheon.uf.common.datastorage.records.DoubleDataRecord;
import com.raytheon.uf.common.datastorage.records.FloatDataRecord;
import com.raytheon.uf.common.datastorage.records.IDataRecord;
import com.raytheon.uf.common.datastorage.records.StringDataRecord;
import com.raytheon.uf.common.derivparam.library.DerivedParameterRequest;
import com.raytheon.uf.common.inventory.tree.CubeLevel;
import com.raytheon.uf.common.python.PythonInterpreter;

/**
 * A script for running the master derived parameter script, which can run any
 * of the derived parameter scripts
 * 
 * <pre>
 * SOFTWARE HISTORY
 * Date          Ticket#  Engineer    Description
 * ------------- -------- ----------- --------------------------
 * Jul 08, 2008           njensen     Initial creation
 * Nov 20, 2009  3387     jelkins     Use derived script's variableId instead of filename
 * Nov 21, 2009  3576     rjpeter     Refactored to populate DerivParamDesc.
 * Oct 29, 2013  2476     njensen     Renamed numeric methods to numpy
 * Apr 11, 2014  2947     bsteffen    Allow returning NaN
 * May 01, 2014  3101     njensen     Safe cast result shape values to Number
 * Apr 20, 2015  4259     njensen     Updated for new Jep API
 * 
 * </pre>
 * 
 * @author njensen
 * @version 1.0
 */

public class MasterDerivScript extends PythonInterpreter {

    protected static final String RESULT = "__result";

    private static final String DATA_NAME = "Data";

    private final Map<Object, List<String>> prevArgs = new HashMap<Object, List<String>>();

    /**
     * Constructor
     * 
     * @param anIncludePath
     *            the python include path
     * @param aClassLoader
     *            the java classloader
     * @param preEvals
     *            python statements to be executed before the script of
     *            aFilePath is run. This should include the statement
     *            "derivParamDir = x" where x is the location of the derived
     *            parameter scripts
     * @throws JepException
     */
    public MasterDerivScript(String anIncludePath, ClassLoader aClassLoader,
            List<String> preEvals) throws JepException {
        super(anIncludePath, aClassLoader, preEvals);
    }

    public Object executeFunction(String name, List<Object> args)
            throws JepException {
        this.prevArgs.clear();
        executeFunctionInternal(name, args);
        jep.eval("globalsRef=globals()");
        for (List<String> pArgs : prevArgs.values()) {
            for (String arg : pArgs) {
                jep.eval(arg + " = None");
                jep.eval("del globalsRef['" + arg + "']");
            }
        }

        this.prevArgs.clear();
        return getExecutionResult();
    }

    private void executeFunctionInternal(String name, List<Object> args)
            throws JepException {
        StringBuilder functionCall = new StringBuilder();
        functionCall.append(RESULT).append(" = execute(");
        for (int i = 0; i < args.size(); i++) {
            if (i != 0) {
                functionCall.append(", ");
            }
            Object arg = args.get(i);
            String argName = "arg" + Integer.toHexString((arg.hashCode()));
            evaluateArgument(argName, arg);
            functionCall.append(argName);

        }
        functionCall.append(")");

        if (name.contains(".")) {
            int lastIdx = name.lastIndexOf(".");
            String functionName = name.substring(lastIdx + 1);
            String path = name.substring(0, lastIdx);
            jep.eval("from " + path + " import " + functionName + " as execute");
        } else {
            jep.eval("from " + name + " import execute");
        }
        jep.eval(functionCall.toString());
    }

    @Override
    protected void evaluateArgument(String argName, Object argValue)
            throws JepException {
        if (prevArgs.containsKey(argValue)) {
            jep.eval(argName + " = " + prevArgs.get(argValue).get(0));
        } else if (argValue instanceof List) {
            @SuppressWarnings({ "rawtypes" })
            List valList = (List) argValue;
            Object val = valList.get(0);
            if (val instanceof CubeLevel) {
                // process as cube
                jep.eval("import numpy");
                jep.eval(argName + " = []");
                boolean cubeCreated = false;
                @SuppressWarnings("unchecked")
                List<CubeLevel<Object, Object>> levelList = valList;
                StringBuilder temp = new StringBuilder();
                for (int i = 0; i < levelList.size(); i++) {
                    CubeLevel<Object, Object> cubeLevel = levelList.get(i);
                    Object press = cubeLevel.getPressure();
                    String pressKey = argName + "_tmpPress";
                    if (press != null) {
                        pressKey += Integer.toHexString((press.hashCode()));
                    }
                    evaluateArgument(pressKey, press);

                    Object param = cubeLevel.getParam();
                    String paramKey = argName + "_tmpParam";
                    if (param != null) {
                        paramKey += Integer.toHexString((param.hashCode()));
                    }
                    evaluateArgument(paramKey, param);

                    if (!cubeCreated) {
                        jep.eval("import numpy");
                        jep.eval("paramShape = " + paramKey + ".shape");
                        jep.eval(argName
                                + ".append(numpy.ndarray(["
                                + valList.size()
                                + ", paramShape[0], paramShape[1]], 'float32'))");
                        // handle pressure shaping
                        temp.setLength(0);
                        temp.append("if type(");
                        temp.append(pressKey);
                        temp.append(") == float:\n\t");
                        temp.append(argName);
                        temp.append(".append(numpy.ndarray([1,");
                        temp.append(valList.size());
                        temp.append("], 'float32'))\nelse:\n\t");
                        temp.append(argName);
                        temp.append(".append(numpy.ndarray([");
                        temp.append(valList.size());
                        temp.append(", paramShape[0], paramShape[1]], 'float32'))");
                        jep.eval(temp.toString());
                        cubeCreated = true;
                    }
                    jep.eval(argName + "[0][" + i + "] = " + paramKey);
                    temp.setLength(0);
                    temp.append("if type(");
                    temp.append(pressKey);
                    temp.append(") == float:\n\t");
                    temp.append(argName);
                    temp.append("[1][0][");
                    temp.append(i);
                    temp.append("] = ");
                    temp.append(pressKey);
                    temp.append("\nelse:\n\t");
                    temp.append(argName);
                    temp.append("[1][");
                    temp.append(i);
                    temp.append("] = ");
                    temp.append(pressKey);
                    jep.eval(temp.toString());
                }
            } else {
                // process as list
                if (valList.size() == 1) {
                    // treat as an unwrapped array
                    evaluateArgument(argName, val);
                } else {
                    // create a list of arrays
                    jep.eval(argName + " = []");
                    for (int argIdx = 0; argIdx < valList.size(); argIdx++) {
                        val = valList.get(argIdx);
                        String argKey = argName + "_" + argIdx;
                        if (val != null) {
                            argKey += "_"
                                    + Integer.toHexString((val.hashCode()));
                        }
                        // setNumpy won't work with indexed objects
                        evaluateArgument(argKey, val);
                        jep.eval(argName + ".append(" + argKey + ")");
                    }
                }
            }
        } else if (argValue instanceof IDataRecord[]) {
            IDataRecord[] valList = (IDataRecord[]) argValue;
            if (valList.length == 1) {
                // treat as an unwrapped array
                evaluateArgument(argName, valList[0]);
            } else {
                // create a list of arrays
                jep.eval(argName + " = []");
                for (int argIdx = 0; argIdx < valList.length; argIdx++) {
                    IDataRecord val = valList[argIdx];
                    jep.eval(argName + ".append(None)");
                    // setNumpy won't work with indexed objects
                    evaluateArgument("__tmp", val);
                    jep.eval(argName + "[" + argIdx + "] = __tmp");
                }
                jep.eval(argName + " = tuple(" + argName + ")");
            }
        } else if (argValue instanceof IDataRecord) {
            setDataRecordArg(argName, (IDataRecord) argValue);
        } else if (argValue instanceof float[]) {
            float[] val = (float[]) argValue;
            NDArray<float[]> arr = new NDArray<>(val, val.length);
            jep.set(argName, arr);
        } else if (argValue instanceof int[]) {
            int[] val = (int[]) argValue;
            NDArray<int[]> arr = new NDArray<>(val, val.length);
            jep.set(argName, arr);
        } else if (argValue instanceof Float) {
            jep.set(argName, (argValue));
        } else if (argValue instanceof DerivedParameterRequest) {
            DerivedParameterRequest request = (DerivedParameterRequest) argValue;
            executeFunctionInternal(request.getMethod(),
                    Arrays.asList(request.getArgumentRecords()));
            jep.eval(argName + "=" + RESULT);
        } else {
            super.evaluateArgument(argName, argValue);
        }
        List<String> pArgs = prevArgs.get(argValue);
        if (pArgs == null) {
            pArgs = new ArrayList<String>();
            prevArgs.put(argValue, pArgs);
        }
        pArgs.add(argName);
    }

    /**
     * Retrieves the result of the method execution
     */
    protected List<?> getExecutionResult() throws JepException {
        // Retrieve the results and return them
        List<IDataRecord> result = null;
        Boolean isTuple = (Boolean) jep.getValue("isinstance(" + RESULT
                + ",tuple)");
        if (isTuple) {
            // figure out how long the tuple is
            int lenTuple = ((Integer) jep.getValue("len(" + RESULT + ")"))
                    .intValue();
            // create result as a list of arrays
            result = new ArrayList<IDataRecord>(lenTuple);
            jep.eval("__tmp = " + RESULT);

            // get each array and put it in the list
            jep.eval(RESULT + " = __tmp[0]");
            getExecutionResult(result, null);
            long[] shape = result.get(0).getSizes();
            for (int tupleElem = 1; tupleElem < lenTuple; tupleElem++) {
                jep.eval(RESULT + " = __tmp[" + tupleElem + "]");
                getExecutionResult(result, shape);
            }
        } else {
            result = new ArrayList<IDataRecord>(1);
            getExecutionResult(result, null);
        }
        jep.eval("del globals()['" + RESULT + "']");
        return result;
    }

    private void filterResult() throws JepException {
        // String conversion
        jep.eval("import numpy");
        StringBuilder script = new StringBuilder();
        script.append("if isinstance(" + RESULT + ", numpy.ndarray) and "
                + RESULT + ".dtype.kind == \"S\":\n");
        script.append("  " + RESULT + "=" + RESULT + ".flatten().tolist()\n");
        jep.eval(script.toString());
        jep.eval("numpy = None");
        jep.eval("del globals()['numpy']");
    }

    private void setDataRecordArg(String argName, IDataRecord argValue)
            throws JepException {
        long[] sizes = argValue.getSizes();
        int[] isizes = new int[sizes.length];
        for (int i = 0; i < sizes.length; i++) {
            /*
             * FIXME BAD! We shouldn't be reversing these, but putting them in
             * the correct order will break any deriv param python math using
             * the location of each point in relation to nearby points.
             * Unfortunately the deriv param math in those cases was altered to
             * account for the incorrect x/y ordering that existed previously.
             */
            // isizes[i] = (int) sizes[i];
            isizes[i] = (int) sizes[sizes.length - 1 - i];
        }
        if (!(argValue instanceof StringDataRecord)) {
            NDArray<?> arr = new NDArray<>(argValue.getDataObject(), isizes);
            jep.set(argName, arr);
            // numpy.NaN only supports float types
            if (argValue instanceof FloatDataRecord
                    || argValue instanceof DoubleDataRecord) {
                jep.eval("import numpy");
                jep.eval(argName + "[" + argName + " <= -9999] = numpy.NaN");
                jep.eval(argName + "[" + argName + " >= 999999] = numpy.NaN");
                jep.eval("numpy = None");
                jep.eval("del globals()['numpy']");
            }
        } else {
            jep.set(argName, argValue);
            jep.eval("import numpy");
            jep.eval(argName + " = numpy.array(" + argName
                    + ".getStringData())");
            jep.set("shape", argValue.getSizes());
            jep.eval(argName + " = " + argName + ".reshape(tuple(shape))");
            jep.eval("numpy = None");
            jep.eval("del globals()['numpy']");
        }
    }

    @SuppressWarnings("unchecked")
    protected void getExecutionResult(List<IDataRecord> result, long[] shape)
            throws JepException {
        filterResult();
        // create result as a list with a single float array
        Object valObj = jep.getValue(RESULT);
        if (valObj instanceof NDArray) {
            NDArray<?> arr = (NDArray<?>) valObj;
            Object data = arr.getData();
            int[] dims = arr.getDimensions();
            long[] sizes = new long[dims.length];
            for (int i = 0; i < dims.length; i++) {
                /*
                 * FIXME BAD! We shouldn't be reversing these, but putting them
                 * in the correct order will break any deriv param python math
                 * using the location of each point in relation to nearby
                 * points. Unfortunately the deriv param math in those cases was
                 * altered to account for the incorrect x/y ordering that
                 * existed previously.
                 */
                // sizes[i] = dims[i];
                sizes[i] = dims[dims.length - 1 - i];
            }
            if (data instanceof double[]) {
                // FIXME double support please? we shouldn't have to do this
                double[] dData = (double[]) data;
                float[] fData = new float[dData.length];
                for (int i = 0; i < dData.length; i++) {
                    fData[i] = (float) dData[i];
                }
                result.add(DataStoreFactory.createStorageRecord(DATA_NAME, "",
                        fData, sizes.length, sizes));
            } else {
                result.add(DataStoreFactory.createStorageRecord(DATA_NAME, "",
                        data, sizes.length, sizes));
            }
        } else if (valObj instanceof List<?>) {
            // the only way to get in here is with Strings
            String[] vals = ((List<String>) valObj).toArray(new String[0]);
            result.add(new StringDataRecord(DATA_NAME, "", vals));
        } else {
            // wrap value in containers to meet return type requirements
            float[] oneVal = new float[1];
            if (!(valObj instanceof Float)) {
                // try to coerce it to a float
                jep.eval(RESULT + " = float(" + RESULT + ")");
                valObj = jep.getValue(RESULT);
            }
            oneVal[0] = ((Float) valObj).floatValue();
            result.add(new FloatDataRecord(DATA_NAME, "", oneVal, 1,
                    new long[] { 1 }));
        }
    }

}
