package com.funfair.api.artists;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/artists")
@CrossOrigin("*")
public class ArtistsController {

	@Autowired
	ArtistsService artistsService;
	@Value("${app.upload.artists-image-path}")
	private String artistImagePath;

	@PostMapping("/add")
	public ResponseEntity<ArtistsDetails> addArtistsDetails(@ModelAttribute AddArtistsDetailsRequestDto addArtistsDetailsRequestDto) throws IOException {
		ArtistsDetails artistsDetails = artistsService.addArtistsDetails(addArtistsDetailsRequestDto , artistImagePath);
		return new ResponseEntity<ArtistsDetails>(artistsDetails, HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<ArtistsDetailsDto>> getAllArtistsDetails() {
		List<ArtistsDetailsDto> response = artistsService.getAllArtistsDetails();
		return new ResponseEntity<List<ArtistsDetailsDto>>(response, HttpStatus.OK);
	}
	@GetMapping("/{artistId}")
	public ResponseEntity<ArtistsDetailsDto> getArtistsDetailsById(@PathVariable String artistId) {
		ArtistsDetailsDto response = artistsService.getArtistsDetailsById(artistId);
		return new ResponseEntity<ArtistsDetailsDto>(response, HttpStatus.OK);
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<ArtistsDetailsDto>> searchArtistsByName(@RequestParam String name) {
		List<ArtistsDetailsDto> response = artistsService.searchArtistsByName(name);
		return new ResponseEntity<List<ArtistsDetailsDto>>(response, HttpStatus.OK);
	}
}
