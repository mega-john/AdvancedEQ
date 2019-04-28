package cs2c.EQ;

public final class SetSoundCommands {
    /*
     * original EQ commands
     */
    public static int BassGain = 1;         //setSound
    public static int MiddleGain = 2;       //setSound
    public static int TrebleGain = 3;       //setSound

    /*
    this three commands always send to sound processor
    0x75  "Loudness Gain"
    0x00 Loudness Gain : 0dB | Loudness HICUT1 |
     */
    public static int LowFreqSB = 11;       //setSound      0xB
    public static int MiddleFreqSB = 12;    //setSound      0xC
    public static int HighFreqSB = 13;      //setSound      0xD
    public static int IncreaseValue = 32;   //setSound      0x20
}
