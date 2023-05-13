package sam.ensat.authentificationsystem;

import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateAxisValueFormatter extends ValueFormatter {

    private final SimpleDateFormat mFormat;

    public DateAxisValueFormatter() {
        mFormat = new SimpleDateFormat("MMM dd HH:mm:ss");
    }

    @Override
    public String getFormattedValue(float value) {
        // Convert the X value from seconds to a formatted duration string
        long millis = TimeUnit.SECONDS.toMillis((long) value);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % 60;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis) % 60;
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
