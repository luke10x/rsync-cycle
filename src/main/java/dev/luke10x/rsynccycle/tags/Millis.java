package dev.luke10x.rsynccycle.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class Millis extends SimpleTagSupport {
    private Long value;

    public void setValue(Long value) {
        this.value = value;
    }

    @Override
    public void doTag() throws JspException, IOException {
        double seconds = (double) value / 1000.0;
        String formatted = String.format("%.3f seconds", seconds);
        JspWriter out = getJspContext().getOut();
        out.print(formatted);
    }
}