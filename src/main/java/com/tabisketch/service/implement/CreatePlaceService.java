package com.tabisketch.service.implement;

import com.tabisketch.bean.entity.Place;
import com.tabisketch.bean.form.CreatePlaceForm;
import com.tabisketch.exception.InsertFailedException;
import com.tabisketch.mapper.IPlacesMapper;
import com.tabisketch.service.ICreatePlaceService;
import org.springframework.stereotype.Service;

@Service
public class CreatePlaceService implements ICreatePlaceService {
    private final IPlacesMapper placesMapper;

    public CreatePlaceService(final IPlacesMapper placesMapper) {
        this.placesMapper = placesMapper;
    }

    @Override
    public void execute(final CreatePlaceForm createPlaceForm) throws InsertFailedException {
        final var place = Place.generate(
                createPlaceForm.getGooglePlaceId(),
                createPlaceForm.getDayId(),
                createPlaceForm.getBudget(),
                createPlaceForm.getStartTime(),
                createPlaceForm.getEndTime(),
                createPlaceForm.getDesiredStartTime(),
                createPlaceForm.getDesiredEndTime(),
                createPlaceForm.getToPolyLine(),
                createPlaceForm.getToTravelTime(),
                createPlaceForm.getToTransportation()
        );

        final int result = this.placesMapper.insert(place);
        if (result != 1) throw new InsertFailedException("Placeの追加に失敗しました。");
    }
}
