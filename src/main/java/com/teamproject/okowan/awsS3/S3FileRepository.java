package com.teamproject.okowan.awsS3;

import org.springframework.data.jpa.repository.JpaRepository;

public interface S3FileRepository extends JpaRepository<S3File, Long> {
}
