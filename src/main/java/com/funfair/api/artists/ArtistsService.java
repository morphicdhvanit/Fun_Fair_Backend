package com.funfair.api.artists;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.funfair.api.common.ImagePathUrl;
import com.funfair.api.common.Util;
import com.funfair.api.exception.BadRequestException;

@Service
public class ArtistsService {

	@Autowired
	ArtistsRespository artistsRespository;
	@Autowired
	Util util;

	public ArtistsDetails addArtistsDetails(AddArtistsDetailsRequestDto addArtistsDetailsRequestDto,
			String artistImagePath) throws IOException {

		boolean exists = artistsRespository.existsByArtistNameIgnoreCase(addArtistsDetailsRequestDto.getArtistName());

		if (exists) {
			throw new BadRequestException("Artist with this name already exists");
		}

		ArtistsDetails artistsDetails = new ArtistsDetails();
		artistsDetails.setArtistId(util.generateRandomNumericId());

		String artistImageUrl = util.saveFile(addArtistsDetailsRequestDto.getArtistImage(), artistImagePath);

		artistsDetails.setArtistImageUrl(artistImageUrl);
		artistsDetails.setArtistName(addArtistsDetailsRequestDto.getArtistName());
		artistsDetails.setArtistBio(addArtistsDetailsRequestDto.getArtistBio());
		artistsDetails.setCreatedBy(addArtistsDetailsRequestDto.getArtistName());
		artistsDetails.setCreatedOn(LocalDateTime.now());

		artistsRespository.save(artistsDetails);

		return artistsDetails;
	}

	public List<ArtistsDetailsDto> getAllArtistsDetails() {
		List<ArtistsDetails> artistsDetails = artistsRespository.findAll();
		List<ArtistsDetailsDto> artistsDetailsDtos = new ArrayList<>();
		for (ArtistsDetails artistsDetail : artistsDetails) {
			ArtistsDetailsDto dto = new ArtistsDetailsDto();
			dto.setArtistId(artistsDetail.getArtistId());
			dto.setArtistName(artistsDetail.getArtistName());
			dto.setArtistBio(artistsDetail.getArtistBio());
			dto.setArtistImageUrl(ImagePathUrl.ARTISTS_IMAGE_PATH+ artistsDetail.getArtistImageUrl());
			artistsDetailsDtos.add(dto);
		}
		return artistsDetailsDtos;
	}

	public ArtistsDetailsDto getArtistsDetailsById(String artistId) {
		ArtistsDetails artistsDetails = artistsRespository.findByArtistId(artistId);
		if (artistsDetails == null) {
			throw new BadRequestException("Artist with this id does not exist");
		}
		ArtistsDetailsDto dto = new ArtistsDetailsDto();
		dto.setArtistId(artistsDetails.getArtistId());
		dto.setArtistName(artistsDetails.getArtistName());
		dto.setArtistBio(artistsDetails.getArtistBio());
		dto.setArtistImageUrl(ImagePathUrl.ARTISTS_IMAGE_PATH+ artistsDetails.getArtistImageUrl());
		return dto;
	}

	public List<ArtistsDetailsDto> searchArtistsByName(String name) {
		List<ArtistsDetails> artistsDetails = artistsRespository.findByArtistNameContainingIgnoreCase(name);
		List<ArtistsDetailsDto> artistsDetailsDtos = new ArrayList<>();
		for (ArtistsDetails artistsDetail : artistsDetails) {
			ArtistsDetailsDto dto = new ArtistsDetailsDto();
			dto.setArtistId(artistsDetail.getArtistId());
			dto.setArtistName(artistsDetail.getArtistName());
			dto.setArtistBio(artistsDetail.getArtistBio());
			dto.setArtistImageUrl(ImagePathUrl.ARTISTS_IMAGE_PATH + artistsDetail.getArtistImageUrl());
			artistsDetailsDtos.add(dto);
		}
		return artistsDetailsDtos;
	}
   
}
