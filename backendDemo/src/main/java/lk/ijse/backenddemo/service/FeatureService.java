package lk.ijse.backenddemo.service;

import lk.ijse.backenddemo.dto.FeatureDTO;
import lk.ijse.backenddemo.entity.Services;

public interface FeatureService {
    FeatureDTO saveFeature(FeatureDTO featureDTO, Services services);

}
