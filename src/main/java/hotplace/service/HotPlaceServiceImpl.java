package hotplace.service;

import attraction.AttractionInfo;
import attraction.repository.AttractionJdbcRepository;
import attraction.repository.AttractionRepository;
import common.FileStore;
import common.exception.HotPlaceException;
import hotplace.HotPlace;
import hotplace.UploadFile;
import hotplace.dto.HotPlaceDto;
import hotplace.dto.HotPlaceListDto;
import hotplace.dto.HotPlaceSearch;
import hotplace.repository.HotPlaceJdbcRepository;
import hotplace.repository.HotPlaceRepository;
import member.Member;
import member.repository.MemberJdbcRepository;
import member.repository.MemberRepository;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class HotPlaceServiceImpl implements HotPlaceService {

    private static final HotPlaceService hotPlaceService = new HotPlaceServiceImpl();

    private final HotPlaceRepository hotPlaceRepository;
    private final MemberRepository memberRepository;
    private final AttractionRepository attractionRepository;

    private HotPlaceServiceImpl() {
        hotPlaceRepository = HotPlaceJdbcRepository.getHotPlaceRepository();
        memberRepository = MemberJdbcRepository.getMemberRepository();
        attractionRepository = AttractionJdbcRepository.getAttractionRepository();
    }

    public static HotPlaceService getHotPlaceService() {
        return hotPlaceService;
    }

    @Override
    public int addHotPlace(Long memberId, int contentId, HotPlaceDto hotPlaceDto) {
        Optional<Member> findMember = memberRepository.findById(memberId);
        if (!findMember.isPresent()) {
            throw new HotPlaceException();
        }

        Optional<AttractionInfo> findAttractionInfo = attractionRepository.findById(contentId);
        if (!findAttractionInfo.isPresent()) {
            throw new HotPlaceException();
        }

        HotPlace hotPlace = HotPlace.builder()
                .contentTypeId(hotPlaceDto.getContentTypeId())
                .name(hotPlaceDto.getName())
                .desc(hotPlaceDto.getDesc())
                .visitedDate(hotPlaceDto.getVisitedDate())
                .uploadFile(
                        UploadFile.builder()
                                .uploadFileName(hotPlaceDto.getUploadFile().getUploadFileName())
                                .storeFileName(hotPlaceDto.getUploadFile().getStoreFileName())
                                .build()
                )
                .build();

        return hotPlaceRepository.save(memberId, contentId, hotPlace);
    }

    @Override
    public List<HotPlaceListDto> searchHotPlace(HotPlaceSearch condition) {
        List<HotPlace> hotPlaces = hotPlaceRepository.findByCondition(condition);
        FileStore fileStore = new FileStore();

        return hotPlaces.stream()
                .map(hotPlace -> HotPlaceListDto.builder()
                        .id(hotPlace.getId())
                        .name(hotPlace.getName())
                        .desc(hotPlace.getDesc())
                        .hit(hotPlace.getHit())
                        .storeFileName(hotPlace.getUploadFile().getStoreFileName())
                        .nickname(hotPlace.getMember().getNickname())
                        .createdDate(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(hotPlace.getCreatedDate()))
                        .build()
                )
                .collect(Collectors.toList());
    }

    @Override
    public int removeHotPlace(Long hotPlaceId, Long memberId) {
        Optional<Member> findMember = memberRepository.findById(memberId);
        if (!findMember.isPresent()) {
            throw new HotPlaceException();
        }

        Optional<HotPlace> findHotPlace = hotPlaceRepository.findById(hotPlaceId);
        if (!findHotPlace.isPresent()) {
            throw new HotPlaceException();
        }
        HotPlace hotPlace = findHotPlace.get();
        if (hotPlace.getMember().getId().equals(memberId)) {
            throw new HotPlaceException();
        }

        return hotPlaceRepository.remove(hotPlaceId);
    }
}
