package engine;

import model.Descriptible;
import attribute.Attribute;
import java.util.List;

public class GowerMetric {

    public static double computeDistance(Descriptible d1, Descriptible d2) {
        List<Attribute> attrs1 = d1.getAttributes();
        double sum = 0.0;
        double weightSum = 0.0;

        for(Attribute<?> a1 : attrs1) {
            Attribute<?> a2 = d2.getAttribute(a1.getName());
            if(a2 != null) {
                double dist = computeSingleDistance(a1, a2);
                sum += dist * a1.getWeight();
                weightSum += a1.getWeight();
            }
        }

        return weightSum == 0 ? 1.0 : sum / weightSum; // distance moyenne pondérée
    }

    private static <T> double computeSingleDistance(Attribute<T> a1, Attribute<?> a2) {
        @SuppressWarnings("unchecked")
        Attribute<T> other = (Attribute<T>) a2;
        return a1.distance(other);
    }
}
