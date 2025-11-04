package embedding;

import java.net.http.*;
import java.net.URI;
import java.util.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 * Client Java pour appeler le pipeline Feature Extraction de Hugging Face
 * afin d'obtenir les vecteurs d'embedding (texte)
 */
public class EmbeddingServiceClient {

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();
    private final String apiKey;

    // Endpoint Feature Extraction (pour obtenir les vecteurs)
    // Modèle Sentence-Transformers populaire
    private static final String FEATURE_EXTRACTION_URL =
        "https://api-inference.huggingface.co/models/BAAI/bge-small-en-v1.5";
    public EmbeddingServiceClient(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * Appelle le pipeline Feature Extraction pour obtenir les embeddings de plusieurs textes.
     * @param texts La liste des chaînes de caractères à "embedder".
     * @return Une liste de vecteurs d'embeddings (chaque vecteur étant une liste de Double).
     */
    public List<List<Double>> embedTexts(List<String> texts) throws Exception {
        Map<String, Object> payloadMap = new HashMap<>();
        payloadMap.put("inputs", texts);
        
        // Optionnel : demander au serveur d'attendre si le modèle est en cours de chargement
/*         Map<String, Object> options = new HashMap<>();
        options.put("wait_for_model", true);
        payloadMap.put("options", options); */

        String payload = mapper.writeValueAsString(payloadMap);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(FEATURE_EXTRACTION_URL))
            .header("Authorization", "Bearer " + apiKey)
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(payload))
            .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException(
                "Erreur API Hugging Face (" + response.statusCode() + ") : " + response.body());
        }

        // Le pipeline renvoie un tableau JSON d'embeddings (chaque embedding est un tableau de nombres)
        TypeReference<List<List<Double>>> typeRef = new TypeReference<List<List<Double>>>(){};
        
        return mapper.readValue(response.body(), typeRef);
    }

    // Exemple d'utilisation
    public static void main(String[] args) {
        try {
            // REMPLACER par votre Token d'API Hugging Face réel
            //String token = ""; 
            EmbeddingServiceClient client = new EmbeddingServiceClient("");

            List<String> inputs = Arrays.asList(
                "The specimen is identified as Phalaenopsis amabilis, a monopodial epiphyte from the Orchidaceae family. The plant exhibits succulent, elliptic leaves and a central, acrotonic growth pattern. The inflorescence is a racemose spike, producing numerous zygomorphic flowers. Key morphological features include a prominent, three-lobed labellum (lip) and a fused reproductive structure, the column (gynostemium). Pigmentation analysis shows a dominant white to creamy hue with minor purple spotting on the proximal labellum surface, indicating high genetic uniformity suitable for mass cultivation.",
                "This cultivar requires strict management under Controlled Environment Agriculture (CEA) conditions. Optimal parameters include a photosynthetically active radiation (PAR) flux density of 100−150 μmol m−2 s−1 and a relative humidity range of 60−75%. The substrate is a porous, moisture-retentive blend of bark and sphagnum moss to ensure high aeration. The shelf-life of the cut flower exceeds 14 days post-harvest, a critical factor for wholesale viability. Its low susceptibility to fungal pathogens (e.g., Fusarium wilt) and its predictable blooming schedule make it a high-value commodity in the global ornamental horticulture market."
            );

            List<List<Double>> embeddings = client.embedTexts(inputs);
            
            System.out.println("Textes embeddés : " + inputs.size());
            System.out.println("Embedding du premier texte (taille : " + embeddings.get(0).size() + ") :");
            // Afficher les 5 premières dimensions du vecteur
            System.out.println("================================= Calcul des similarités ==============================");
            System.out.println(EmbeddingServiceClient.cosineSimilarity(embeddings.get(0), embeddings.get(1)));
            //System.out.println(EmbeddingServiceClient.cosineSimilarity(embeddings.get(0), embeddings.get(2)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static Double cosineSimilarity(List<Double> vect1, List<Double> vect2) {
        Double similarity = null;
        
        if (vect1.size() != vect2.size() || 
            (vect1.isEmpty() || vect2.isEmpty())) { // Utilisation de isEmpty()
                System.out.println("Erreur sur la taille des vecteurs. Veuillez vérifier qu'ils sont de même taille et non nuls !\n");
        } else {
            int n = vect1.size();

            Double prod = 0.0;
            Double normV1_squared = 0.0; 
            Double normV2_squared = 0.0; 
            
            for(int i = 0; i < n; i++) {
                prod += vect1.get(i) * vect2.get(i);
                normV1_squared += vect1.get(i) * vect1.get(i);
                normV2_squared += vect2.get(i) * vect2.get(i);
            }
            
            Double normV1 = Math.sqrt(normV1_squared);
            Double normV2 = Math.sqrt(normV2_squared);

            if (normV1 != 0.0 && normV2 != 0.0) {
                similarity = prod / (normV1 * normV2);
            } else {
                similarity = 0.0; 
            }
        }

        return similarity;
}
}