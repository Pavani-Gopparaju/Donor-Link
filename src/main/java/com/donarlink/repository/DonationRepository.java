package com.donarlink.repository;

import com.donarlink.model.Donation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DonationRepository extends CrudRepository<Donation, Integer> {

    List<Donation> getDonationsByDonor_Id(int donorId);
}
