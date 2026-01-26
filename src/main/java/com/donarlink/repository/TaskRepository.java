package com.donarlink.repository;

import com.donarlink.model.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Integer> {
    List<Task> findByNgo_Id(int ngoId);
}
