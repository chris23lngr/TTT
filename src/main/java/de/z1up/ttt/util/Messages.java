package de.z1up.ttt.util;

public final class Messages {

    public static final String PREFIX = "§4TTT §8×§7" + " ";
    public static final String NO_PERM = PREFIX + "§cDazu hast du keine Rechte§8.";

    public static final String CMD_NOT_EXECUTABLE_ATM = PREFIX + "§cDu kannst diese Kommand derzeit nicht ausführen§8.";
    public static final String WRON_USAGE = PREFIX + "§cFalsche Nutzung. Verwende §4";

    // Command Build
    public static final String BUILD_ACTIVATED = PREFIX + "§aDu befindest dich jetzt im Baumodus§8.";
    public static final String BUILD_DEACTIVATED = PREFIX + "§cDu befindest dich jetzt nicht mehr im Baumodus§8.";

    // Command Add Map
    public static final String AM_NO_ITEM_IN_HAND = PREFIX + "§cDu musst ein Item in deiner Hand halten§8.";
    public static final String AM_MAT_INVALID = PREFIX + "§cDas ausgewählte Material ist invalide§8.";
    public static final String AM_MAP_ALREADY_EXIST = PREFIX + "§cEs exestiert bereits eine Map mit diesem Namen§8.";
    public static final String AM_SUCCESS = PREFIX + "§aMap wurde erstellt§8. §aName: §7";


    public static final String MAP_NOT_EXIST = PREFIX + "§cDiese Map exestiert nicht§8.";

    public static final String FM_SUCCESS = PREFIX + "§aMap wurde erfolgreich gesetzt§8. §aEs wird gespielt auf §b";

    public static final String MAP_NOT_SET = PREFIX + "§cFehler! Es wurde keine Map gefunden§8!";

    public static final String PLAYING_ON = PREFIX + "§7Es wird gespielt auf §a";

    public static final String ID_NOT_EXISTS = PREFIX + "§cDiese ID exestiert nicht§8.";

    public static final String ATTRIBUTE_NOT_NUMERIC = PREFIX + "§cDas Attribut muss aus einer Zahl besetehen§8.";

    public static final String SPAWN_SET = PREFIX + "§aEs wurde ein neuer Spawn erstellt auf der Map §b";
    public static final String SPAWN_DELETED = PREFIX + "§aDer ausgewählte Spawn wurde gelöscht§8. §aID§8: §b";

    public static final String FS_ALREADY_ACTIVATED = PREFIX + "§cDer §bForce Start §cwurde bereits aktiviert§8!";
    public static final String FS_SUCCESS = PREFIX + "§aDer §bForce Start §awurde aktiviert§8!";

    public final class ErrorMessages {

        public static final String PREFIX = "§4ERROR §8-> §c";

        public static final String ID_NOT_FOUND_EXC = PREFIX + "Cannot find id.";
        public static final String ID_ALREADY_EXIST_EXC = PREFIX + "ID already exists.";

        public static final String NAME_NOT_FOUND_EXC = PREFIX + "Cannot find name.";
        public static final String NAME_ALREADY_EXIST_EXC = PREFIX + "Name already exists.";
    }

}
