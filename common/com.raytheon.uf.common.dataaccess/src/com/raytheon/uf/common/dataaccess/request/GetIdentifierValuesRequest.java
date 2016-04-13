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
package com.raytheon.uf.common.dataaccess.request;

import com.raytheon.uf.common.dataaccess.IDataRequest;
import com.raytheon.uf.common.serialization.annotations.DynamicSerialize;
import com.raytheon.uf.common.serialization.annotations.DynamicSerializeElement;

/**
 * Request to retrieve the allowed values for a particular identifier of a
 * datatype from the Data Access Framework.
 * 
 * <pre>
 * 
 * SOFTWARE HISTORY
 * 
 * Date         Ticket#    Engineer    Description
 * ------------ ---------- ----------- --------------------------
 * Apr 13, 2016 5379       tgurney     Initial creation
 * 
 * </pre>
 * 
 * @author tgurney
 */

@DynamicSerialize
public class GetIdentifierValuesRequest extends AbstractDataAccessRequest {

    @DynamicSerializeElement
    protected String identifierKey;

    public GetIdentifierValuesRequest() {
        super();
    }

    public GetIdentifierValuesRequest(final IDataRequest request) {
        super(request);
    }

    public String getIdentifierKey() {
        return identifierKey;
    }

    public void setIdentifierKey(String identifierKey) {
        this.identifierKey = identifierKey;
    }
}
