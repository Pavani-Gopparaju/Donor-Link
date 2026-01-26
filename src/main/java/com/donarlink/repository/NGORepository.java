package com.donarlink.repository;

import com.donarlink.model.NGO;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NGORepository extends CrudRepository<NGO, Integer> {

    NGO getNGOByAdmin_Id(int adminId);

    java.util.Optional<NGO> findByAdmin_Id(int adminId);
}
