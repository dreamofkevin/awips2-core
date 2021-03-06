##
# This software was developed and / or modified by Raytheon Company,
# pursuant to Contract DG133W-05-CQ-1067 with the US Government.
#
# U.S. EXPORT CONTROLLED TECHNICAL DATA
# This software product contains export-restricted data whose
# export/transfer/disclosure is restricted by U.S. law. Dissemination
# to non-U.S. persons whether in the United States or abroad requires
# an export license or other authorization.
#
# Contractor Name:        Raytheon Company
# Contractor Address:     6825 Pine Street, Suite 340
#                         Mail Stop B8
#                         Omaha, NE 68106
#                         402.291.0100
#
# See the AWIPS II Master Rights File ("Master Rights File.pdf") for
# further licensing information.
##

# ----------------------------------------------------------------------------
# Calculate wind chill from Temperature and u and v Wind (or wind speed).
#
# ----------------------------------------------------------------------------

#
# SOFTWARE HISTORY
#
# Date           Ticket#      Engineer      Description
# ------------   ----------   -----------   -----------
#                             ????          Initial creation
# Aug 05, 2015   4703         njensen       Optimized
#

from numpy import where, less_equal

def execute(T, wSp):
   "This tool derives the Wind Chill index from Temperature and Wind Speed"
   mag = wSp * 1.15
   WndChl = where(less_equal(mag, 3), T, 35.74 + (0.6215 * T) -
               (35.75 * (mag ** 0.16)) + (0.4275 * T * (mag ** 0.16)))
   # clip values where WindChill > T
   mask = (WndChl > T)
   WndChl[mask] = T[mask]
   # substitute the temperature if WindChill >= 51 degrees
   mask = (T >= 51)
   WndChl[mask] = T[mask]
   return WndChl
