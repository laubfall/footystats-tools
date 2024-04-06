package de.footystats.tools.services.prediction.heatmap;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;

@BsonDiscriminator(key = StatsBetResultDistribution.DISCRIMINATOR_KEY, value = "IntegerStatsDistribution")
public class IntegerStatsDistribution extends StatsBetResultDistribution<Integer> {

}
