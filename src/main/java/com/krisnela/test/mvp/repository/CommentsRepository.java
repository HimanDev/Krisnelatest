package com.krisnela.test.mvp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.krisnela.test.mvp.dto.Comments;

public interface CommentsRepository extends MongoRepository<Comments		, String>{

}
