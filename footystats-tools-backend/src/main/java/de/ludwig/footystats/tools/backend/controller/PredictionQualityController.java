package de.ludwig.footystats.tools.backend.controller;

import de.ludwig.footystats.tools.backend.services.prediction.quality.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/predictionquality")
public class PredictionQualityController {

	private PredictionQualityService predictionQualityService;

	private PredictionQualityReportRepository predictionQualityReportRepository;

	public PredictionQualityController(PredictionQualityService predictionQualityService, PredictionQualityReportRepository predictionQualityReportRepository) {
		this.predictionQualityService = predictionQualityService;
		this.predictionQualityReportRepository = predictionQualityReportRepository;
	}

	@GetMapping("/compute")
	public PredictionQualityReport computeQuality(){
		return predictionQualityService.computeQuality();
	}

	@GetMapping("/latest/revision")
	public PredictionQualityRevision latestRevision(){
		var report = predictionQualityReportRepository.findTopByOrderByRevisionDesc();
		if(report == null){
			return PredictionQualityRevision.NO_REVISION;
		}
		return report.getRevision();
	}

	@PostMapping(name = "/precast", consumes = {"application/json"}, produces = {"application/json"})
	public Precast precast(@RequestBody PredictionQualityRevision revision){
		return predictionQualityService.precast(revision);
	}

	@GetMapping("/latest/report")
	public PredictionQualityReport latestReport(){
		return predictionQualityReportRepository.findTopByOrderByRevisionDesc();
	}

	@PostMapping(name = "/recompute", consumes = {"application/json"}, produces = {"application/json"})
	public PredictionQualityReport recomputeQuality(PredictionQualityRevision revision){
		return predictionQualityService.recomputeQuality(revision);
	}
}
