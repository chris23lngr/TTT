package de.z1up.ttt.util;

/**
 * All messages that can be used globally are
 * stored in the Messages class. User or context
 * specific messages should not be stored here.
 *
 * @author chris23lngr
 * @since 1.0
 */
public final class Messages {

    public static final String PREFIX
            = "§4TTT §8×§7" + " ";

    public static final String NO_PERM
            = PREFIX + "§cDazu hast du keine Rechte§8.";

    public static final String CMD_NOT_EXECUTABLE_ATM
            = PREFIX + "§cDu kannst diese Kommand derzeit nicht ausführen§8.";

    public static final String WRONG_USAGE
            = PREFIX + "§cFalsche Nutzung. Verwende §4";

    public static final String BUILD_ACTIVATED
            = PREFIX + "§aDu befindest dich jetzt im Baumodus§8.";

    public static final String BUILD_DEACTIVATED
            = PREFIX + "§cDu befindest dich jetzt nicht mehr im Baumodus§8.";

    public static final String AM_NO_ITEM_IN_HAND
            = PREFIX + "§cDu musst ein Item in deiner Hand halten§8.";

    public static final String AM_MAT_INVALID
            = PREFIX + "§cDas ausgewählte Material ist invalide§8.";

    public static final String AM_MAP_ALREADY_EXIST
            = PREFIX + "§cEs exestiert bereits eine Map mit diesem Namen§8.";

    public static final String AM_SUCCESS
            = PREFIX + "§aMap wurde erstellt§8. §aName: §7";

    public static final String MAP_NOT_EXIST
            = PREFIX + "§cDiese Map exestiert nicht§8.";

    public static final String FM_SUCCESS
            = PREFIX + "§aMap wurde erfolgreich gesetzt§8. §aEs wird gespielt auf §b";

    public static final String MAP_NOT_SET
            = PREFIX + "§cFehler! Es wurde keine Map gefunden§8!";

    public static final String PLAYING_ON
            = PREFIX + "§7Es wird gespielt auf §b";

    public static final String ID_NOT_EXISTS
            = PREFIX + "§cDiese ID exestiert nicht§8.";

    public static final String ATTRIBUTE_NOT_NUMERIC
            = PREFIX + "§cDas Attribut muss aus einer Zahl besetehen§8.";

    public static final String SPAWN_SET
            = PREFIX + "§aEs wurde ein neuer Spawn erstellt auf der Map §b";

    public static final String SPAWN_DELETED
            = PREFIX + "§aDer ausgewählte Spawn wurde gelöscht§8. §aID§8: §b";

    public static final String FS_ALREADY_ACTIVATED
            = PREFIX + "§cDer §bForce Start §cwurde bereits aktiviert§8!";

    public static final String FS_SUCCESS
            = PREFIX + "§aDer §bForce Start §awurde aktiviert§8!";

    public static final String MAPS_YOU_VOTED_FOR
            = PREFIX + "§aAuswahl gespeichert§8. §aDu hast gevotet für §b";

    public static final String ACHIEVEMENT_ACHIEVED
            = PREFIX + "§aDu hast einen Erfolg erzielt§8: §b";

    public static final String VOTE_PERIOD_NOT_ACTIVE
            = PREFIX + "§cDie Votephase ist derzeit nicht aktiv§8.";

    public static final String NO_KICK_PLAYER_FOUND
            = PREFIX + "§cEs wurde kein Spieler zum kicken gefunden§8!";

    public static final String PREMIUM_NEEDED
            = PREFIX + "§cUm vollen runden beizutreten, benötigst du mindestens den §6Pemium §c-Rang§8.";

    public static final String KICKED_BY_PREMIUM
            = PREFIX + "§cDu wurdest von einem §6Premium §c-Spieler gekickt§8.";

    public static final String WIN_TRAITOR
            = PREFIX + "§aDie §4Traitor §agewinnen diese Runde§8!";

    public static final String WIN_INNO
            = PREFIX + "§aDie Innocents gewinnen diese Runde§8!";

    public static final String WIN_DRAW
            = PREFIX + "§7Diese Runde ist unentschieden asugegangen§8!";

    public static final String TEAM_PLAYING_AS_TRAITOR
            = PREFIX + "§7Du bist jetzt im Team der §4Traitor§8.";

    public static final String TEAM_PLAYING_AS_DETECTIVE
            = PREFIX + "§7Du bist jetzt im Team der §9Detectives§8.";

    public static final String TEAM_PLAYING_AS_INNOCENT
            = PREFIX + "§7Du bist jetzt im Team der §aInncoents§8.";

    public static final String EC_NOT_OPEN_ATM
            = PREFIX + "§cDie Enderchest kann erst nach der Schutzphase geöffnet werden§8.";

    public static final String DEAD_BODY_NOT_PROCESSED
            = PREFIX + "§cDiese Leiche wurde noch nicht bearbeitet.";

    public static final String DEAD_BODY_OF
            = PREFIX + "§7Das ist die Leiche von §e";

    public static final String DEAD_BODY_WAS
            = PREFIX + "§7Er/Sie war ein §e";

    public static final String NOT_MIN_PLAYERS
            = PREFIX + "§cEs müssen mindestens 2 Spieler online sein um Spielen zu können§8.";

    public static final String PLAYER_NOT_EXISTS
            = PREFIX + "§cDieser Spieler exestriert nicht§8.";

    public static final String PLAYING_AS_SPEC
            = PREFIX + "§7Du bist jetzt ein Zuschauer§8.";

    public static final String GAME_LEFT
            = PREFIX + "§cDu hast das Spiel verlassen§8.";

    public static final String HOLO_SET
            = PREFIX + "§7Das §cStats-Hologramm §7wurde gesetzt§8.";

    public final class ErrorMessages {

        public static final String PREFIX
                = "§4ERROR §8-> §c";

        public static final String ID_NOT_FOUND_EXC
                = PREFIX + "Cannot find id.";

        public static final String ID_ALREADY_EXIST_EXC
                = PREFIX + "ID already exists.";

        public static final String NAME_NOT_FOUND_EXC
                = PREFIX + "Cannot find name.";

        public static final String NAME_ALREADY_EXIST_EXC
                = PREFIX + "Name already exists.";

        public static final String OBJ_OF_WRONG_TYPE_EXC
                = PREFIX + "This type of object doesn't fit right here.";
    }

    public final class ItemNames {

        public static final String NAVIGATOR
                = "§6Navigator";

        public static final String MAP_VOTING
                = "§bMap Voting";

        public static final String QUIT_GAME
                = "§cSpiel verlassen";

        public static final String SETTINGS
                = "§aEinstellungen";

        public static final String ACHIEVEMENTS
                = "§eErfolge";

        public static final String IDENTIFIER
                = "§fPersonen Identifizierer";

        public static final String SHOP
                = "§4Shop";

        public static final String PLAYER_SETTINGS
                = "§9Deine Einstellungen";

        public static final String TICKETS
                = "§4Tickets";

        public static final String TICKET_TRAITOR
                = "§4Traitor Tickets";

        public static final String TICKET_DETECTIVE
                = "§9Detective Tickets";

        public static final String TICKET_INNOCENT
                = "§aInnocent Tickets";

    }

    public final class Scoreboards {

        public static final String SB_DISPLAY
                = "§8« §crenixinside.de §8»";

    }

}
