package industries.werwolf.training.layers.ui.base.ui.component;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.theme.lumo.LumoUtility;
import industries.werwolf.training.layers.service.util.ProgressListener;
import org.jspecify.annotations.Nullable;

import java.time.Instant;

public class ProgressDialog extends Dialog implements ProgressListener {

    private static final long SECOND = 1000L;
    private static final long MINUTE = SECOND * 60;
    private static final long HOUR = MINUTE * 60;
    private static final long DAY = HOUR * 24;

    private final ProgressBar progressBar = new ProgressBar();
    private final Span eta = new Span();
    private final Span status = new Span();
    private boolean showPercentage = false;

    @Nullable
    private String unit;

    @Nullable
    private Instant started = null;

    public ProgressDialog() {
        eta.setVisible(false);

        status.addClassName(LumoUtility.Margin.Left.AUTO);

        Icon icon = VaadinIcon.COG.create();
        icon.setSize("36px");
        icon.addClassNames("rotate", LumoUtility.AlignSelf.CENTER);

        FlexLayout content = new FlexLayout(progressBar, new FlexLayout(eta, status), icon);
        content.setFlexDirection(FlexLayout.FlexDirection.COLUMN);
        content.setAlignItems(FlexComponent.Alignment.STRETCH);
        content.addClassName(LumoUtility.Gap.MEDIUM);

        add(content);
        setWidth(400, Unit.PIXELS);
        setCloseOnEsc(false);
        setCloseOnOutsideClick(false);
        setModal(true);
    }

    @Override
    public void open() {
        super.open();
        progressBar.setIndeterminate(true);
        eta.setVisible(false);
        status.setText("");
        started = null;
    }

    @Override
    public void setProgress(double max, double current) {
        Instant now = Instant.now();
        if (started == null) {
            started = now;
        }

        progressBar.setVisible(true);
        progressBar.setIndeterminate(false);
        progressBar.setMax(max);
        progressBar.setValue(current);

        if(showPercentage) {
            status.setText("%01.01f%%".formatted(current * 100.0 / max));
        }
        else {
            status.setText(current + "/" + max + (unit == null ? "" : " " + unit));
        }

        if (started != null && started.plusSeconds(10).isBefore(now)) {
            eta.setVisible(true);
            eta.setText("ETA " + getEta(max, current, started, now));
        }
    }

    private String getEta(double max, double current, Instant started, Instant now) {
        double timePassed = now.toEpochMilli() - started.toEpochMilli();
        double timeLeft = (timePassed / current) * (max - current);
        return formatTime(timeLeft);
    }

    private String formatTime(double milliseconds) {
        double time = milliseconds;

        if(time < SECOND) {
            return "0s";
        }

        StringBuilder sb = new StringBuilder();

        if (time >= DAY) {
            long days = Math.round(time / DAY);
            sb.append(days).append(" days");
            time = time - DAY * days;
        }

        if (time >= HOUR) {
            long hours = Math.round(time / HOUR);
            if (!sb.isEmpty()) {
                sb.append(" ");
            }
            sb.append(hours).append("h");
            time = time - HOUR * hours;
        }

        if (time >= MINUTE) {
            long minutes = Math.round(time / MINUTE);
            if (sb.isEmpty()) {
                sb.append(" ");
            }
            sb.append(minutes).append("m");
            time = time - MINUTE * minutes;
        }

        if (time >= SECOND) {
            long seconds = Math.round(time / SECOND);
            if (sb.isEmpty()) {
                sb.append(" ");
            }
            sb.append(seconds).append("s");
        }

        return sb.toString();
    }

    public ProgressDialog withTitle(String title) {
        setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        setHeaderTitle(title);
    }

    public ProgressDialog withUnit(String unit) {
        setUnit(unit);
        return this;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public ProgressDialog withShowPercentage(boolean showPercentage) {
        setShowPercentage(showPercentage);
        return this;
    }

    public void setShowPercentage(boolean showPercentage) {
        this.showPercentage = showPercentage;
    }
}
