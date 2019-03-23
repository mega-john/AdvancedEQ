package cs2c.EQ1;


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
     * for sending new commands to sound processor must use set_volume
     */

    public static int BassQF = 23;      //0x17
    public static int MiddleQF = 24;    //0x18
    public static int TrebleQF = 25;    //0x19
    public static int LoudQF = 26;      //0x1a
    public static int Preamp = 27;      //0x1b
    public static int unk1 = 28;      //0x1c
    public static int unk2 = 29;      //0x1d
    public static int unk3 = 30;      //0x1e
    public static int unk4 = 31;      //0x1f

    /*
     * original EQ commands
     */
    public static int BassGain = 1;         //setSound
    public static int MiddleGain = 2;       //setSound
    public static int TrebleGain = 3;       //setSound
    public static int SubwooferGain = 6;    //set_volume

    /*
    this three commands always send to sound processor
    0x75  "Loudness Gain"
    0x00 Loudness Gain : 0dB | Loudness HICUT1 |
     */
    public static int LowFreqSB = 11;       //setSound
    public static int MiddleFreqSB = 12;    //setSound
    public static int HighFreqSB = 13;      //setSound

    public static int LoudOnOff = 22;       //set_volume
    public static int IncreaseValue = 32;   //setSound
}

/*
регистры используемые в оригинальном hesi_mcu.bin (D:\!ida analyze mcu\decrypted_copy.bin)

Address       Function  Instruction
-------       --------  -----------
IROM:0000FA8B sub_FA86  MOVW    0x180, DW0      ; Initial Setup
IROM:0000FA93 sub_FA86  MOVW    0x280, DW0      ; LPF Setup
IROM:0000F9A4 sub_F987  MOVW    0x380, DW0      ; Mixing Setup
IROM:0000F9C4 sub_F987  MOVW    0x380, DW0      ; Mixing Setup
IROM:0000FA9B sub_FA86  MOVW    0x380, DW0      ; Mixing Setup
IROM:0000F795 sub_F709  MOVW    0x580, DW0      ; Input Selector
IROM:0000F99C sub_F987  MOVW    0x580, DW0      ; Input Selector
IROM:0000F79B sub_F709  MOVW    0x680, DW0      ; Input Gain
IROM:0000F7B6           MOVW    0x680, DW0      ; Input Gain
IROM:0000F957 sub_F922  MOVW    0x2080, DW0     ; Volume Gain
IROM:0000F82B sub_F815  MOVW    0x2080, DW0     ; Volume Gain
IROM:0000F80D sub_F805  MOVW    0x2080, DW0     ; Volume Gain
IROM:0000F7FE sub_F7D0  MOVW    0x2080, DW0     ; Volume Gain
IROM:0000FA18 sub_F9CC  MOVW    0x2880, DW0     ; Fader 1ch Front
IROM:0000F90D           MOVW    0x2880, DW0     ; Fader 1ch Front
IROM:0000F9F1 sub_F9CC  MOVW    0x2980, DW0     ; Fader 2ch Front
IROM:0000F907           MOVW    0x2980, DW0     ; Fader 2ch Front
IROM:0000FA66 sub_F9CC  MOVW    0x2A80, DW0     ; Fader 1ch Rear
IROM:0000F919           MOVW    0x2A80, DW0     ; Fader 1ch Rear
IROM:0000FA3F sub_F9CC  MOVW    0x2B80, DW0     ; Fader 2ch Rear
IROM:0000F913           MOVW    0x2B80, DW0     ; Fader 2ch Rear
IROM:0000F843 sub_F832  MOVW    0x2C80, DW0     ; Fader 1ch Sub
IROM:0000F980           MOVW    0x3080, DW0     ; Mixing (2ch Sub)
IROM:0000F95D sub_F922  MOVW    0x3080, DW0     ; Mixing (2ch Sub)
IROM:00014AAE john      MOVW    0x4180, DW0     ; Bass setup
IROM:0000F84F           MOVW    0x4180, DW0     ; Bass setup
IROM:00014AB4 john      MOVW    0x4480, DW0     ; Middle setup
IROM:0000F857           MOVW    0x4480, DW0     ; Middle setup
IROM:00014ABB john      MOVW    0x4780, DW0     ; Treble setup
IROM:0000F85F           MOVW    0x4780, DW0     ; Treble setup
IROM:0000F897 sub_F866  MOVW    0x5180, DW0     ; Bass Gain
IROM:0000F8AC sub_F89E  MOVW    0x5480, DW0     ; Middle Gain
IROM:0000F8E5 sub_F8B4  MOVW    0x5780, DW0     ; Treble Gain
IROM:00014AC1 john      MOVW    0x7580, DW0     ; Loudness Gain
IROM:0000F8FA sub_F8EC  MOVW    0x7580, DW0     ; Loudness Gain
IROM:0000FAA3 sub_FA86  MOVW    0x9080, DW0     ; Move Word
IROM:000122FE sub_12211 MOVW    0x9680, DW0     ; Move Word
IROM:00011693 sub_114C0 MOVW    0x9680, DW0     ; Move Word

 */