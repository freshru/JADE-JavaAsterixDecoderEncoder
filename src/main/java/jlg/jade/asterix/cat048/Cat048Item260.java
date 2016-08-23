/**
 * Created by alexandru on 7/14/16.
 */
package jlg.jade.asterix.cat048;

import jlg.jade.asterix.AsterixItemLength;
import jlg.jade.asterix.FixedLengthAsterixData;
import jlg.jade.common.ModeCCode;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Cat048Item260 - ACAS Resolution Advisory Report
 * Currently active Resolution Advisory (RA), if any, generated by the
 * ACAS associated with the transponder transmitting the report and
 * threat identity data.
 */
public class Cat048Item260 extends FixedLengthAsterixData {
    private TCASVersion determinedTCASVersion = TCASVersion.VERSION_604;
    private int threatTypeIndicator;            // TTI
    private int TIDModeSAddress;                // TID ModeS Address
    private int multiThreatIndicator;           // MTI / MTE
    private int raTerminated;                   // RAT

    private List<String> RAComplementList = new ArrayList<>();         // RAC

    private int ARABit41;
    private int ARABit42;
    private int ARABit43;
    private int ARABit44;
    private int ARABit45;
    private int ARABit46;
    private int ARABit47;
    private int ARABit48;
    private int ARABit49;
    private int ARABit50;
    private String auralCode;
    private List<String> ARAList = new ArrayList<>();
    private int TIDAltitude;
    private int TIDRange;
    private int TIDBearing;

    public Cat048Item260() {
    }

    /**
     * This constructor is used for passing the value of the BDS Register 1,0 bit 39 ( bit71 in the
     * BDS 1,0 Data Link Capability Report for
     * Guidance Material for Mode S-Specific Protocol Application Avionics / Table A-7 )
     * TODO: Further inquiries are needed to differentiate between TCAS 7.0 and 7.1
     *
     * @param bdsRegister10Bit39
     */
    public Cat048Item260(int bdsRegister10Bit39) {
        if (bdsRegister10Bit39 == 1) {
            this.determinedTCASVersion = TCASVersion.VERSION_70;
        } else {
            this.determinedTCASVersion = TCASVersion.VERSION_604;
        }
    }


    @Override
    protected int setSizeInBytes() {
        return AsterixItemLength.SEVEN_BYTES.getValue();
    }

    @Override
    protected void decodeFromByteArray(byte[] input, int offset) {
        BitSet bs = BitSet.valueOf(
                new byte[]{input[offset], input[offset + 1], input[offset + 2], input[offset + 3],
                        input[offset + 4], input[offset + 5], input[offset + 6]});

        // TTI
        final int TTI_BIT1_INDEX = 27;
        final int TTI_BIT0_INDEX = 26;

        int TTIBit1Value = 0;
        int TTIBit0Value = 0;

        if (bs.get(TTI_BIT1_INDEX)) {
            TTIBit1Value = 1;
        }

        if (bs.get(TTI_BIT0_INDEX)) {
            TTIBit0Value = 1;
        }

        this.threatTypeIndicator = TTIBit1Value * 2 + TTIBit0Value;

        appendItemDebugMsg("TTI", this.threatTypeIndicator);

        // when TTI = 1 then TID should contain a ModeS Address
        if (this.threatTypeIndicator == 1) {

            // create a String Builder to store the binary representation of the last 4 bytes for item260
            StringBuilder sb = new StringBuilder();
            for (int i = 3; i <= 6; i++) {
                // for each of the last 4 bytes in the input append the zero padded representation to the string builder
                String byteBinaryRepresentation = Integer.toBinaryString(input[offset + i]);
                String zeroPaddedBinaryRepresentation = ("00000000" + byteBinaryRepresentation)
                        .substring(byteBinaryRepresentation.length());

                sb.append(zeroPaddedBinaryRepresentation);
            }

            // extract from the string builder the 24 bits
            String TIDModeSAddressRepresentation = sb.toString().substring(7, 30);

            // parse the extracted 24 bits binary represented string to get the ModeS Address
            this.TIDModeSAddress = Integer.parseInt(TIDModeSAddressRepresentation, 2);

            appendItemDebugMsg("TID ModeS Address", this.TIDModeSAddress);

        }

        // when TTI = 2 then TID should contain altitude, range and bearing
        if (this.threatTypeIndicator == 2) {
            // TIDAltitude ModeC bits
            int modeCAltitudeCodeBitA1 = 0;
            int modeCAltitudeCodeBitA2 = 0;
            int modeCAltitudeCodeBitA4 = 0;
            int modeCAltitudeCodeBitB1 = 0;
            int modeCAltitudeCodeBitB2 = 0;
            int modeCAltitudeCodeBitB4 = 0;
            int modeCAltitudeCodeBitC1 = 0;
            int modeCAltitudeCodeBitC2 = 0;
            int modeCAltitudeCodeBitC4 = 0;
            // TID Altitude ModeC bit D1 is never used according to ICAO documentation
            int modeCAltitudeCodeBitD1 = 0;
            int modeCAltitudeCodeBitD2 = 0;
            int modeCAltitudeCodeBitD4 = 0;

            // TID Altitude ModeC indexes in order
            final int BIT_C1_INDEX = 25;
            final int BIT_A1_INDEX = 24;
            final int BIT_C2_INDEX = 39;
            final int BIT_A2_INDEX = 38;
            final int BIT_C4_INDEX = 37;
            final int BIT_A4_INDEX = 36;
            final int BIT_B1_INDEX = 34;
            final int BIT_B2_INDEX = 32;
            final int BIT_D2_INDEX = 47;
            final int BIT_B4_INDEX = 46;
            final int BIT_D4_INDEX = 45;

            // TID Range bits
            int tidRangeBit1Value = 0;
            int tidRangeBit2Value = 0;
            int tidRangeBit3Value = 0;
            int tidRangeBit4Value = 0;
            int tidRangeBit5Value = 0;
            int tidRangeBit6Value = 0;
            int tidRangeBit7Value = 0;

            // TID Range indexes in bit set
            final int BIT_TID_RANGE1_INDEX = 44;
            final int BIT_TID_RANGE2_INDEX = 43;
            final int BIT_TID_RANGE3_INDEX = 42;
            final int BIT_TID_RANGE4_INDEX = 41;
            final int BIT_TID_RANGE5_INDEX = 40;
            final int BIT_TID_RANGE6_INDEX = 55;
            final int BIT_TID_RANGE7_INDEX = 54;

            // TID Bearing
            int tidBearingBit1Value = 0;
            int tidBearingBit2Value = 0;
            int tidBearingBit3Value = 0;
            int tidBearingBit4Value = 0;
            int tidBearingBit5Value = 0;
            int tidBearingBit6Value = 0;

            // TID Bearing indexes in bit set
            final int BIT_TID_BEARING1_INDEX = 53;
            final int BIT_TID_BEARING2_INDEX = 52;
            final int BIT_TID_BEARING3_INDEX = 51;
            final int BIT_TID_BEARING4_INDEX = 50;
            final int BIT_TID_BEARING5_INDEX = 49;
            final int BIT_TID_BEARING6_INDEX = 48;

            // altitude
            // set individual Gray Code bits
            // Mode C altitude code of threat. Bit ordering is
            // C1 A1 C2 A2 C4 A4 0 B1 D1 B2 D2 B4 D4

            if (bs.get(BIT_C1_INDEX)) {
                modeCAltitudeCodeBitC1 = 1;
            }

            if (bs.get(BIT_A1_INDEX)) {
                modeCAltitudeCodeBitA1 = 1;
            }

            if (bs.get(BIT_C2_INDEX)) {
                modeCAltitudeCodeBitC2 = 1;
            }

            if (bs.get(BIT_A2_INDEX)) {
                modeCAltitudeCodeBitA2 = 1;
            }

            if (bs.get(BIT_C4_INDEX)) {
                modeCAltitudeCodeBitC4 = 1;
            }

            if (bs.get(BIT_A4_INDEX)) {
                modeCAltitudeCodeBitA4 = 1;
            }

            if (bs.get(BIT_B1_INDEX)) {
                modeCAltitudeCodeBitB1 = 1;
            }

            // bit D1 is never used so we don't have an index and we do not check it

            if (bs.get(BIT_B2_INDEX)) {
                modeCAltitudeCodeBitB2 = 1;
            }

            if (bs.get(BIT_D2_INDEX)) {
                modeCAltitudeCodeBitD2 = 1;
            }

            if (bs.get(BIT_B4_INDEX)) {
                modeCAltitudeCodeBitB4 = 1;
            }

            if (bs.get(BIT_D4_INDEX)) {
                modeCAltitudeCodeBitD4 = 1;
            }

            // build 2 Strings representing the GrayCode 500ft and 100ft increments

            // 500ft increments are stored in bits D2 D4 A1 A2 A4 B1 B2 B4
            StringBuilder fiveHundredIncrementsBuilder = new StringBuilder();
            fiveHundredIncrementsBuilder.append(modeCAltitudeCodeBitD2)
                    .append(modeCAltitudeCodeBitD4).append(modeCAltitudeCodeBitA1)
                    .append(modeCAltitudeCodeBitA2).append(modeCAltitudeCodeBitA4)
                    .append(modeCAltitudeCodeBitB1).append(modeCAltitudeCodeBitB2)
                    .append(modeCAltitudeCodeBitB4);

            int fiveHundredIncrementsGrayCode = Integer
                    .parseInt(fiveHundredIncrementsBuilder.toString(), 2);

            // 100ft increments are stored in bits C1 C2 C4
            StringBuilder oneHundredIncrementsBuilder = new StringBuilder();
            oneHundredIncrementsBuilder.append(modeCAltitudeCodeBitC1)
                    .append(modeCAltitudeCodeBitC2).append(modeCAltitudeCodeBitC4);

            int oneHundredIncrementsGrayCode = Integer
                    .parseInt(oneHundredIncrementsBuilder.toString(), 2);

            // pass the 2 variables to the ModeC Gray Code to feet calculator
            this.TIDAltitude = ModeCCode
                    .grayCodeToFeet(fiveHundredIncrementsGrayCode, oneHundredIncrementsGrayCode);

            appendItemDebugMsg("TID Altitude", this.TIDAltitude);

            // range
            if (bs.get(BIT_TID_RANGE1_INDEX)) {
                tidRangeBit1Value = 1;
            }

            if (bs.get(BIT_TID_RANGE2_INDEX)) {
                tidRangeBit2Value = 1;
            }

            if (bs.get(BIT_TID_RANGE3_INDEX)) {
                tidRangeBit3Value = 1;
            }

            if (bs.get(BIT_TID_RANGE4_INDEX)) {
                tidRangeBit4Value = 1;
            }

            if (bs.get(BIT_TID_RANGE5_INDEX)) {
                tidRangeBit5Value = 1;
            }

            if (bs.get(BIT_TID_RANGE6_INDEX)) {
                tidRangeBit6Value = 1;
            }

            if (bs.get(BIT_TID_RANGE7_INDEX)) {
                tidRangeBit7Value = 1;
            }

            StringBuilder tidRangeBinaryRepresentation = new StringBuilder();
            tidRangeBinaryRepresentation.append(tidRangeBit1Value).append(tidRangeBit2Value)
                    .append(tidRangeBit3Value).append(tidRangeBit4Value).append(tidRangeBit5Value)
                    .append(tidRangeBit6Value).append(tidRangeBit7Value);

            this.TIDRange = Integer.parseInt(tidRangeBinaryRepresentation.toString(), 2);

            appendItemDebugMsg("TID Range", this.TIDRange);

            // bearing
            if (bs.get(BIT_TID_BEARING1_INDEX)) {
                tidBearingBit1Value = 1;
            }

            if (bs.get(BIT_TID_BEARING2_INDEX)) {
                tidBearingBit2Value = 1;
            }

            if (bs.get(BIT_TID_BEARING3_INDEX)) {
                tidBearingBit3Value = 1;
            }

            if (bs.get(BIT_TID_BEARING4_INDEX)) {
                tidBearingBit4Value = 1;
            }

            if (bs.get(BIT_TID_BEARING5_INDEX)) {
                tidBearingBit5Value = 1;
            }

            if (bs.get(BIT_TID_BEARING6_INDEX)) {
                tidBearingBit6Value = 1;
            }

            StringBuilder tidBearingBinaryRepresentation = new StringBuilder();
            tidBearingBinaryRepresentation.append(tidBearingBit1Value).append(tidBearingBit2Value)
                    .append(tidBearingBit3Value).append(tidBearingBit4Value)
                    .append(tidBearingBit5Value).append(tidBearingBit6Value);

            this.TIDBearing = Integer.parseInt(tidBearingBinaryRepresentation.toString(), 2);

            appendItemDebugMsg("TID Bearing", this.TIDBearing);
        }

        // ARA - bits 41-50
        final int ARA_BIT41_INDEX = 15;
        final int ARA_BIT42_INDEX = 14;
        final int ARA_BIT43_INDEX = 13;
        final int ARA_BIT44_INDEX = 12;
        final int ARA_BIT45_INDEX = 11;
        final int ARA_BIT46_INDEX = 10;
        final int ARA_BIT47_INDEX = 9;
        final int ARA_BIT48_INDEX = 8;
        final int ARA_BIT49_INDEX = 23;
        final int ARA_BIT50_INDEX = 22;

        if (bs.get(ARA_BIT41_INDEX)) {
            this.ARABit41 = 1;
        }

        appendItemDebugMsg("ARABit41", this.ARABit41);

        if (bs.get(ARA_BIT42_INDEX)) {
            this.ARABit42 = 1;
        }

        appendItemDebugMsg("ARABit42", this.ARABit42);

        if (bs.get(ARA_BIT43_INDEX)) {
            this.ARABit43 = 1;
        }

        appendItemDebugMsg("ARABit43", this.ARABit43);

        if (bs.get(ARA_BIT44_INDEX)) {
            this.ARABit44 = 1;
        }

        appendItemDebugMsg("ARABit44", this.ARABit44);

        if (bs.get(ARA_BIT45_INDEX)) {
            this.ARABit45 = 1;
        }

        appendItemDebugMsg("ARABit45", this.ARABit45);

        if (bs.get(ARA_BIT46_INDEX)) {
            this.ARABit46 = 1;
        }

        appendItemDebugMsg("ARABit46", this.ARABit46);

        if (bs.get(ARA_BIT47_INDEX)) {
            this.ARABit47 = 1;
        }

        appendItemDebugMsg("ARABit47", this.ARABit47);

        if (bs.get(ARA_BIT48_INDEX)) {
            this.ARABit48 = 1;
        }

        appendItemDebugMsg("ARABit49", this.ARABit49);


        appendItemDebugMsg("ARABit48", this.ARABit48);

        if (bs.get(ARA_BIT49_INDEX)) {
            this.ARABit49 = 1;
        }

        appendItemDebugMsg("ARABit49", this.ARABit49);

        if (bs.get(ARA_BIT50_INDEX)) {
            this.ARABit50 = 1;
        }

        appendItemDebugMsg("ARABit50", this.ARABit50);

        // MTI / MTE
        final int MTI_BIT_INDEX = 28;

        if (bs.get(MTI_BIT_INDEX)) {
            this.multiThreatIndicator = 1;
        }

        appendItemDebugMsg("MTI", this.multiThreatIndicator);

        // Aural

        AuralCalculator auralCalculator = new AuralCalculator();
        this.auralCode = auralCalculator.determineAuralCode(this, this.determinedTCASVersion);

        appendItemDebugMsg("Aural code", this.auralCode);

        // ARA List
        ARACalculator araCalculator = new ARACalculator();
        this.ARAList.addAll(araCalculator.getARAList(this, this.determinedTCASVersion));

        if (!ARAList.isEmpty()) {
            String ARAListRepresentation = String.join(",", ARAList.stream()
                    .map(s -> s.toString())
                    .collect(Collectors.toList()));
            appendItemDebugMsg("ARA list", ARAListRepresentation);
        }

        // RAT
        final int RAT_BIT_INDEX = 29;

        if (bs.get(RAT_BIT_INDEX)) {
            this.raTerminated = 1;
        }

        appendItemDebugMsg("RAT", this.raTerminated);

        // RAC
        final int RAC_BIT1_INDEX = 17;
        final int RAC_BIT2_INDEX = 16;
        final int RAC_BIT3_INDEX = 31;
        final int RAC_BIT4_INDEX = 30;

        if (bs.get(RAC_BIT1_INDEX)) {
            this.RAComplementList.add("Do not pass below");
        }

        if (bs.get(RAC_BIT2_INDEX)) {
            this.RAComplementList.add("Do not pass above");
        }

        if (bs.get(RAC_BIT3_INDEX)) {
            this.RAComplementList.add("Do not turn left");
        }

        if (bs.get(RAC_BIT4_INDEX)) {
            this.RAComplementList.add("Do not turn right");
        }

        if (!RAComplementList.isEmpty()) {
            String RAComplementListRepresentation = String.join(",", RAComplementList.stream()
                    .map(s -> s.toString()).collect(Collectors.toList()));
            appendItemDebugMsg("RA complements list", RAComplementListRepresentation);
        }
    }

    @Override
    protected String setDisplayName() {
        return "Cat048Item260 - ACAS Resolution Advisory Report";
    }

    public int getThreatTypeIndicator() {
        return threatTypeIndicator;
    }

    public int getARABit41() {
        return ARABit41;
    }

    public int getARABit42() {
        return ARABit42;
    }

    public int getARABit43() {
        return ARABit43;
    }

    public int getARABit44() {
        return ARABit44;
    }

    public int getARABit45() {
        return ARABit45;
    }

    public int getARABit46() {
        return ARABit46;
    }

    public int getARABit47() {
        return ARABit47;
    }

    public int getARABit48() {
        return ARABit48;
    }

    public int getARABit49() {
        return ARABit49;
    }

    public int getARABit50() {
        return ARABit50;
    }

    public int getMultiThreatIndicator() {
        return multiThreatIndicator;
    }

    public int getRaTerminated() {
        return raTerminated;
    }

    public List<String> getRAComplementList() {
        return RAComplementList;
    }

    /**
     * @return Returns the ModeS Address contained in the Threat Identity Data when Threat Type Indicator is 1
     */
    public int getTIDModeSAddress() {
        return TIDModeSAddress;
    }

    public int getTIDAltitude() {
        return TIDAltitude;
    }

    public int getTIDRange() {
        return TIDRange;
    }

    public int getTIDBearing() {
        return TIDBearing;
    }

    public String getAuralCode() {
        return auralCode;
    }

    public List<String> getARAList() {
        return ARAList;
    }
}
