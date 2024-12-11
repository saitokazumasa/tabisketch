package com.tabisketch.service.implement;

import com.tabisketch.bean.entity.Plan;
import com.tabisketch.bean.entity.User;
import com.tabisketch.mapper.IPlansMapper;
import com.tabisketch.mapper.IUsersMapper;
import com.tabisketch.service.IListPlanService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListPlanService implements IListPlanService {
    private final IUsersMapper usersMapper;
    private final IPlansMapper plansMapper;

    public ListPlanService(
            final IUsersMapper usersMapper,
            final IPlansMapper plansMapper
    ) {
        this.usersMapper = usersMapper;
        this.plansMapper = plansMapper;
    }

    @Override
    public List<Plan> execute(final String mailAddress) {
        final User user = usersMapper.selectByMailAddress(mailAddress);
        return plansMapper.selectByUserId(user.getId());
    }
}
