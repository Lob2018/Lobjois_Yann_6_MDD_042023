package fr.soft64.mddapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.soft64.mddapi.model.Subject;
import fr.soft64.mddapi.repository.SubjectRepository;

@Service
public class SubjectService {

	@Autowired
	private SubjectRepository subjectRepository;

	public final Optional<Subject> findSubjectById(final Long id) {
		if (id == null) {
			throw new Error();
		}
		return subjectRepository.findById(id);
	}

	public final Iterable<Subject> getAllSubjects() {
		return subjectRepository.findAll();
	}

}
