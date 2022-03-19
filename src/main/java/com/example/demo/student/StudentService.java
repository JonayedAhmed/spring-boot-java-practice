package com.example.demo.student;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service 
public class StudentService {
	
	private final StudentRepository studentRepository;
	
	@Autowired
	public StudentService(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	
	public List<Student> getStudents() {
		return studentRepository.findAll();
	}
	
	public void addNewStudent(Student student) {
		Optional<Student> studentByOptional = studentRepository.findStudentByEmail(student.getEmail());
		
		 if(studentByOptional.isPresent()) {
			 throw new IllegalStateException("Email Taken");
		 }
		 studentRepository.save(student);
	}
	
	public void deleteStudent(Long id){
		Optional<Student> studentToDelete = studentRepository.findById(id);
		
		 if(studentToDelete.isPresent()) {
			 studentRepository.deleteById(id);
		 }
		 else {
			 throw new IllegalStateException("Student is not present with id "+id);
		}
	}
	
	public void updateStudent(Student student, Long id) throws EntityNotFoundException{
		Optional<Student> studentToUpdate = studentRepository.findById(id);
		
		if(studentToUpdate.isPresent()) {
			
			Student s = studentToUpdate.get();
			BeanUtils.copyProperties(student, s);
			s.setId(id);
			
			studentRepository.save(s);
		 }
		else {
			throw new EntityNotFoundException("Student is not present with id "+id);
		}
		
	}
}
