package lk.ijse.backenddemo.service.impl;


import lk.ijse.backenddemo.dto.FeatureDTO;
import lk.ijse.backenddemo.entity.Feature;
import lk.ijse.backenddemo.entity.Services;
import lk.ijse.backenddemo.repo.FeatureRepository;
import lk.ijse.backenddemo.service.FeatureService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeatureServiceImpl implements FeatureService {

    @Autowired
    private FeatureRepository featureRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public FeatureDTO saveFeature(FeatureDTO featureDTO, Services services) {
        Feature feature = new Feature();
        feature.setServices(services);
        feature.setName(featureDTO.getName());
        feature.setLogo(featureDTO.getLogo());
        feature = featureRepository.save(feature);
        return modelMapper.map(feature, FeatureDTO.class);

    }
}
