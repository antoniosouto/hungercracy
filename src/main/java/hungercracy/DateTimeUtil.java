/**
 * 
 */
package hungercracy;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @author Antonio
 * Static class with Date/Time related functions
 */
public final class DateTimeUtil {
	
	private DateTimeUtil () {
	}
	
	private static ZoneId zoneId = ZoneId.of( "Brazil/East" );
	
	private static ZonedDateTime getCurrentZonedDateTime() {
		return ZonedDateTime.ofInstant( Instant.now() , zoneId );
	}
	
	public static LocalDate getCurrentZonedLocalDate() {
		return LocalDate.from(getCurrentZonedDateTime());
	}
	
	public static LocalDate getCurrentZonedLocalDateMinusDays(final long days) {
		return LocalDate.from(getCurrentZonedDateTime()).minusDays(days);
	}
	
	public static boolean isPastLocalTime(int hour, int minute) {
		return ((getCurrentZonedDateTime().getHour() > hour) ||
				(getCurrentZonedDateTime().getHour() == hour && getCurrentZonedDateTime().getMinute() > minute));
	}
}
