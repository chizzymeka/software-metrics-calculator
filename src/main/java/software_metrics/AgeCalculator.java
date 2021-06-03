package software_metrics;

import classes.Sourcefile;

import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class AgeCalculator {

    public long calculateAgeInWeeks(Sourcefile sourcefile) {

        long ageInWeeks = 0;
        FileTime dateCreated = sourcefile.getLastModifiedDateFileTime();
        Instant dateCreatedInstant = dateCreated.toInstant();
        Instant nowInstant = Instant.now();
        LocalDateTime startDate = LocalDateTime.ofInstant(dateCreatedInstant, ZoneId.systemDefault());
        LocalDateTime endDate = LocalDateTime.ofInstant(nowInstant, ZoneId.systemDefault());
        ageInWeeks = ChronoUnit.WEEKS.between(startDate, endDate);

        return ageInWeeks;
    }
}
