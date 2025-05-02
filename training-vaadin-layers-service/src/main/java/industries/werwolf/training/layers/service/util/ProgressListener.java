package industries.werwolf.training.layers.service.util;

@FunctionalInterface
public interface ProgressListener {
    void setProgress(double max, double current);
}
