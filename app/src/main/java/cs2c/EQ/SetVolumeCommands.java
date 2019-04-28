package cs2c.EQ;

public final class SetVolumeCommands {
    /*
     * new EQ commands
     * for sending new commands to sound processor must use set_volume
     */

    public static int BassQF = 23;      //0x17
    public static int MiddleQF = 24;    //0x18
    public static int TrebleQF = 25;    //0x19

    public static int BassG = 26;      //0x1A
    public static int MiddleG = 27;      //0x1B
    public static int TrebleG = 28;        //0x1C
    public static int LoudG = 29;        //0x1D
//    public static int unk3 = 30;        //0x1E
//    public static int unk4 = 31;        //0x1F

    public static int LoudnessGain = 6;    //set_volume
    public static int LoudOnOff = 22;       //set_volume    0x16
}
