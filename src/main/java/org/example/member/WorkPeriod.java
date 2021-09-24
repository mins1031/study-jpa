package org.example.member;

import javax.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable
public class WorkPeriod {

    private LocalDate startDate;
    private LocalDate endDate;

    public WorkPeriod() {
    }

    public WorkPeriod(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
