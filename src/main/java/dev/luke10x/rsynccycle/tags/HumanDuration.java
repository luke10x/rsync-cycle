package dev.luke10x.rsynccycle.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.time.Duration;

public class HumanDuration extends SimpleTagSupport {
    private Duration duration;

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    @Override
    public void doTag() throws JspException, IOException {
        String durationNice = getDurationNice(duration);
        JspWriter out = getJspContext().getOut();
        out.print(durationNice);
    }

    private String getDurationNice(Duration duration) {
        long seconds = duration.getSeconds();
        if (seconds < 60) {
            return String.format("%d seconds", seconds);
        }
        long minutes = seconds / 60;
        if (minutes < 60) {
            return String.format("%d minutes, %d seconds", minutes, seconds % 60);
        }
        long hours = minutes / 60;
        if (hours < 24) {
            return String.format("%d hours, %d minutes", hours, minutes % 60);
        }
        long days = hours / 24;
        if (days < 7) {
            return String.format("%d days, %d hours", days, hours % 24);
        }
        long weeks = days / 7;
        if (weeks < 52) {
            return String.format("%d weeks, %d days", weeks, days % 7);
        }
        long years = weeks / 52;
        return String.format("%d years, %d weeks", years, weeks % 52);
    }
}