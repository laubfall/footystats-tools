package de.ludwig.footystats.tools.backend.controller.quality;

import de.ludwig.footystats.tools.backend.services.prediction.quality.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

	@PostMapping(name = "/precast", consumes = {"application/json"}, produces = {"application/json"}, path = {"/precast"})
	public Precast precast(@RequestBody PredictionQualityRevision revision){
		return predictionQualityService.precast(revision);
	}

	@GetMapping("/latest/report")
	public PredictionQualityReport latestReport(){
		var report = predictionQualityReportRepository.findTopByOrderByRevisionDesc();
		if(report != null){
			return report;
		}

		throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	}

	@PostMapping(name = "/recompute", consumes = {"application/json"}, produces = {"application/json"}, path = {"/recompute"})
	public PredictionQualityReport recomputeQuality(@RequestBody PredictionQualityRevision revision){
		var report = predictionQualityService.recomputeQuality(revision);

		if(report == null){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

		return report;
	}
}
