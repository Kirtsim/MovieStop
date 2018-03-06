package fm.kirtsim.kharos.moviestop.db.typeCoverter.movie;

import android.arch.persistence.room.TypeConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kharos on 06/03/2018
 */

public final class StatusConverter {
    private static final String DELIM = "&";

    @SuppressWarnings("StringConcatenationInLoop")
    @TypeConverter
    public String statusListToStatusString(List<Character> statuses) {
        if (statuses == null || statuses.size() == 0)
            return "";

        String string = String.valueOf(statuses.get(0));
        for (int i = 0; i < statuses.size(); ++i)
            string += DELIM + statuses.get(i);

        return string;
    }

    @TypeConverter
    public List<Character> statusStringToList(String stringData) {
        if (stringData == null)
           return new ArrayList<>(0);

        final String [] strStatuses = stringData.split(DELIM);
        final List<Character> statuses = new ArrayList<>(strStatuses.length);

        for (String status : strStatuses)
            if (status != null && !status.isEmpty())
                statuses.add(status.charAt(0));

        return statuses;
    }
}
