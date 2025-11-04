package utils.embedding;

public class SimilarityUtils {

    public static double cosine(double[] a, double[] b) {
        if (a.length != b.length) throw new IllegalArgumentException("Dimension mismatch");
        double dot = 0, normA = 0, normB = 0;
        for (int i = 0; i < a.length; i++) {
            dot += a[i] * b[i];
            normA += a[i] * a[i];
            normB += b[i] * b[i];
        }
        return dot / (Math.sqrt(normA) * Math.sqrt(normB));
    }
}
