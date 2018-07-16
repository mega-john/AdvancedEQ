package cs2c.EQ;


/*
    AdvancedSwitch = R01;
    SubwooferSetup = R02;
    LoudnessFrequency = R03;
    InputSelector = R05;
    InputGain = R06;
    VolumeGain = R20;
    FaderFrontRight = R28;
    FaderFrontLeft = R29;
    FaderRearRight = R2A;
    FaderRearLeft = R2B;
    FaderSubwoofer = R2C;
    MixingGain = R30;

    EqBassSetup = R41;
    EqMiddleSetup = R44;
    EqTrebleSetup = R47;

    EqBassGain = R51;
    EqMiddleGain = R54;
    EqTrebleGain = R57;
    LoudnessGainHiCut = R75;

    на данный моммент реализован следующий маппинг(android->mcu):
    23(0x17)->0x41
    24(0x18)->0x44
    25(0x19)->0x47
    26(0x1A)->0x75
 */

public final class Commands {
    /*
     * new EQ commands
     */

    public static int BassQF = 23;
    public static int MiddleQF = 24;
    public static int TrebleQF = 25;
    public static int LoudQF = 26;
    public static int Preamp = 27;

    /*
     * original EQ commands
     */
    public static int BassGain = 1;
    public static int MiddleGain = 2;
    public static int TrebleGain = 3;
    public static int SubwooferGain = 6;

    public static int LowFreqSB = 11;
    public static int MiddleFreqSB = 12;
    public static int HighFreqSB = 13;

    public static int LoudOnOff = 22;
    public static int IncreaseValue = 32;
}
