package fm.kirtsim.kharos.moviestop.db.typeCoverter.movie;

import android.arch.persistence.room.TypeConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kharos on 06/03/2018
 */

public final class GenreIdsTypeConverter {

    private static final String DELIM = "&";

    @TypeConverter
    public List<Integer> stringToGenreIdsList(String strData) {
        if (strData == null)
            strData = "";
        String[] strIds = strData.split(DELIM);

        final List<Integer> ids = new ArrayList<>(strIds.length);
        try {
            for (String strId : strIds)
                if (strId != null && !strId.isEmpty())
                    ids.add(Integer.valueOf(strId));
        } catch (NumberFormatException e) {
            System.err.println("Could not parse genre ids from String to a List<Integer>");
            e.printStackTrace();
            ids.clear();
        }
        return ids;
    }

    @TypeConverter
    public String listOfGenreIdsToString(List<Integer> ids) {
        if (ids == null || ids.size() == 0)
            return "";

        final StringBuilder builder = new StringBuilder(String.valueOf(ids.get(0)));
        for (int i = 1; i < ids.size(); ++i) {
            builder.append(DELIM);
            builder.append(ids.get(i));
        }

        return builder.toString();
    }
}
