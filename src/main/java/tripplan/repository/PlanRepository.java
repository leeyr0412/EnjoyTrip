package tripplan.repository;

import tripplan.DetailPlan;
import tripplan.dto.PlanListDto;
import tripplan.dto.PlanSearch;
import tripplan.TripPlan;

import java.util.List;
import java.util.Optional;

public interface PlanRepository {

    int save(TripPlan tripPlan);

    int save(DetailPlan detailPlan);

    Optional<TripPlan> findById(Long tripPlanId);

    Optional<DetailPlan> findByDetailPlanId(Long detailPlanId);

    List<TripPlan> findAllByMemberId(Long memberId);

    List<DetailPlan> findAllByTripPlanId(Long tripPlanId);

    List<PlanListDto> findByCondition(PlanSearch condition, int pageNum, int amount);

    Long findByMemberId(Long memberId);

    int findTotalCount();

    int updateTripPlan(Long tripPlanId, TripPlan tripPlan);

    int removeTripPlan(Long tripPlanId);

    int removeDetailPlan(Long detailPlanId);

    void clear();
}

