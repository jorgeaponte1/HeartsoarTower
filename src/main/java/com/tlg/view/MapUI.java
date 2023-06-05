package com.tlg.view;

import com.tlg.model.Room;

import java.util.List;

public class MapUI {
    private final static int line_width = 34;
    static String bedroom =
            "+--------------------------------+\n" +
                    "|          [       }}}           |\n" +
                    "|        [           }}          |\n" +
                    "|       [  Bedroom ++  }         |\n" +
                    "|       [       +--+|  +------+  |\n" +
                    "|       [-------+---+--+------+  |\n";
    static String notBedroom =
            "+--------------------------------+\n" +
                    "|                                |\n" +
                    "|                                |\n" +
                    "|                                |\n" +
                    "|                                |\n" +
                    "|                                |\n";
    static String conservatory =
            "|      ++          __  |         |\n" +
                    "|      ||         |<>| |         |\n" +
                    "|      ||Conservatory  |         |\n" +
                    "|      ++----+   +-----+         |\n" +
                    "|            |   |               |\n";
    static String notConservatory =
            "|                                |\n" +
                    "|                                |\n" +
                    "|                                |\n" +
                    "|                                |\n" +
                    "|                                |\n";
    static String ballroom =
            "|         +--+   +----+          |\n" +
                    "|         |  Ballroom |     +-+-++\n";
    static String notBallroom =
            "|                                |\n" +
                    "|                                +\n";
    static String vamp =
            "|         +-----------++----+ | ||\n" +
                    "|         |            | Vamp   ||\n" +
                    "|         |           ++--------++\n" ;
    static String notVamp =
            "|                                |\n" +
                    "|                                |\n" +
                    "|                                +\n" ;
    static String parlor =
            "|         |  Parlor   |          |\n" +
                    "|         |           |          |\n";
    static String notParlor =
            "|                                |\n" +
                    "|                                |\n";
    static String den =
            "|         +-----------+          |\n" +
                    "|         |           |          |\n" +
                    "|         |   Den     |          |\n" +
                    "|         |           |          |\n";
    static String notDen =
            "|                                |\n" +
                    "|                                |\n" +
                    "|                                |\n" +
                    "|                                |\n";
    static String plaisure =
            "|         |     |     |          |\n" +
                    "|         |  X  |     |          |\n" +
                    "|         |  X  |     |          |\n";
    static String notPlaisure =
            "|                                |\n" +
                    "|                                |\n" +
                    "|                                |\n";
    static String kitchen =
            "| |  Entrance           Kitchen| |\n" +
                    "+-+----------------------------+-+\n";
    static String notKitchen =
            "|  | Entrance                    |\n" +
                    "+--------------------------------+\n";
    static String foyer =
            "|         |     |     |          |\n" +
                    "| +-------+   Foyer   +--------+ |\n";
    static String notFoyer =
            "|                                |\n" +
                    "|                                |\n";
    static String entrance =
            "| |  Entrance                  | |\n" +
                    "+-+----------------------------+-+\n";

    private final static String FULL_MAP = "+--------------------------------+\n" +
            "|          [       }}}           |\n" +
            "|        [           }}          |\n" +
            "|       [  Bedroom ++  }         |\n" +
            "|       [       +--+|  +------+  |\n" +
            "|       [-------+---+--+------+  |\n" +
            "|      ++          __  |         |\n" +
            "|      ||         |<>| |         |\n" +
            "|      ||Conservatory  |         |\n" +
            "|      ++----+   +-----+         |\n" +
            "|            |   |               |\n" +
            "|         +--+   +----+          |\n" +
            "|         |  Ballroom |     +-+-++\n" +
            "|         +-----------++----+ | ||\n" +
            "|         |            | Vamp   ||\n" +
            "|         |           ++--------++\n" +
            "|         |  Parlor   |          |\n" +
            "|         |           |          |\n" +
            "|         +-----------+          |\n" +
            "|         |           |          |\n" +
            "|         |   Den     |          |\n" +
            "|         |           |          |\n" +
            "|         +-----+-----+          |\n" +
            "|         |     |     |          |\n" +
            "|         |  X  |     |          |\n" +
            "|         |  X  |     |          |\n" +
            "|         |     |     |          |\n" +
            "| +-------+   Foyer   +--------+ |\n" +
            "| |  Entrance           Kitchen| |\n" +
            "+-+----------------------------+-+\n";

    public static String getMap(List<Room> rooms) {
        StringBuilder sb = new StringBuilder();
        if (rooms.get(9).isDiscovered()) {
            sb.append(bedroom);
        } else {
            sb.append(notBedroom);
        }
        if (rooms.get(8).isDiscovered()) {
            sb.append(conservatory);
        } else {
            sb.append(notConservatory);
        }
        if (rooms.get(7).isDiscovered()) {
            sb.append(ballroom);
        } else {
            sb.append(notBallroom);
        }
        if (rooms.get(6).isDiscovered()) {
            sb.append(vamp);
        } else {
            sb.append(notVamp);
        }
        if (rooms.get(5).isDiscovered()) {
            sb.append(parlor);
        } else {
            sb.append(notParlor);
        }
        if (rooms.get(4).isDiscovered()) {
            sb.append(den);
        } else {
            sb.append(notDen);
        }
        if (rooms.get(3).isDiscovered()) {
            sb.append(plaisure);
        } else {
            sb.append(notPlaisure);
        }
        if (rooms.get(1).isDiscovered()) {
            sb.append(foyer);
        } else {
            sb.append(notFoyer);
        }
        if (rooms.get(2).isDiscovered()) {
            sb.append(kitchen);
        } else {
            sb.append(notKitchen);
        }
        return sb.toString();
    }

    public static String getFullMap() {
        return FULL_MAP;
    }
}