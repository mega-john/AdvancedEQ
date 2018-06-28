package cs2c.EQ;

/**
 * Created by john on 05.11.2017.
 */

public final class Constants {
    public static String sb_bassQProgress = "sb_bassQProgress";
    public static String sb_bassFoProgress = "sb_bassFoProgress";
    public static String sb_middleQProgress = "sb_middleQProgress";
    public static String sb_middleFoProgress = "sb_middleFoProgress";
    public static String sb_trebleQProgress = "sb_trebleQProgress";
    public static String sb_trebleFoProgress = "sb_trebleFoProgress";

    public static String sb_bassGProgress = "sb_lowFreqProgress";
    public static String sb_middleGProgress = "sb_middleGProgress";
    public static String sb_trebleGProgress = "sb_trebleGProgress";

    public static String sb_preampGProgress = "sb_preampGProgress";
    public static String sb_LoudGProgress = "sb_LoudGProgress";
    public static String cb_LoudOn = "cb_LoudOn";

    public static int cBassQFCommand = 23;
    public static int cMiddleQFCommand = 24;
    public static int cTrebleQFCommand = 25;
    public static int cLoudQFCommand = 26;

    public static int cBassCommand = 1;
    public static int cMiddleCommand = 2;
    public static int cTrebleCommand = 3;
    public static int cSubwooferGainCommand = 6;

    public static int cLowFreqSB = 11;
    public static int cMiddleFreqSB = 12;
    public static int cHighFreqSB = 13;

    public static int cLoudOnOffCommand = 22;
    public static int cIncreaseValueCommand = 32;

    public static String EQInterfaceName = "eq";
    public static String EQSettingsFileName = "musicEQ";
}
