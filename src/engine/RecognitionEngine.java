package engine;

import model.Observation;
import model.Typology;
import model.Descriptible;

import utils.engine.TypologyDistance;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RecognitionEngine {

    private List<Typology> typologies;

    public RecognitionEngine(List<Typology> typologies) {
        this.typologies = typologies;
    }

    public List<Typology> recognize(Observation obs) {
        List<TypologyDistance> results = new ArrayList<>();

        for(Typology typo : typologies) {
            double dist = GowerMetric.computeDistance(obs, typo);
            System.out.println(dist);
            results.add(new TypologyDistance(typo, dist));
        }

        Collections.sort(results, Comparator.comparingDouble(TypologyDistance::getDistance));

        List<Typology> sortedTypologies = new ArrayList<>();
        for(TypologyDistance td : results) sortedTypologies.add(td.getTypologie());

        return sortedTypologies;
    }
}
