package com.devsato.tickets.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.devsato.tickets.domain.entities.Event;
import com.devsato.tickets.domain.entities.EventStatusEnum;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
  Page<Event> findByOrganizerId(UUID organizerId, Pageable pageable);

  Optional<Event> findByIdAndOrganizerId(UUID id, UUID organizerId);

  Page<Event> findByStatus(EventStatusEnum status, Pageable pageable);

  @Query(value = "SELECT * FROM events WHERE " +
      "status = 'PUBLISHED' AND " +
      "to_tsvector('english', COALESCE(name, '') || '' || COALESCE(venue, '')) " +
      "@@ plainto_tsquery('english', :searchTerm)", countQuery = "SELECT count(*) FROM events WHERE " +
          "status = 'PUBLISHED' AND " +
          "to_tsvector('english', COALESCE(name, '') || COALESCE(venue, '')) " +
          "@@ plainto_tsquery('english', :searchTerm)", nativeQuery = true)
  Page<Event> searchEvents(@Param("searchTerm") String searchTerm, Pageable pageable);

  Optional<Event> findByIdAndStatus(UUID id, EventStatusEnum status);
}
