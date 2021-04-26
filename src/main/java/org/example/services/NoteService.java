package org.example.services;

import org.example.model.Note;
import org.example.repositories.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;


    public List<Note> getAllNotes(){
        return noteRepository.findAll();
    }

    public Optional<Note> getNote(Long id){
        return noteRepository.findById(id);
    }

    public void createNote(Note note){

        Note newNote = new Note();
        newNote.setTitle(note.getTitle());
        newNote.setDate(note.getDate());
        newNote.setStatus(note.isStatus());

        noteRepository.saveAndFlush(newNote);
    }

    public void updateNote(Long id, Note note){
        Note noteDb = noteRepository.findById(id).orElse(null);

        if(noteDb != null){
            noteDb.setTitle(note.getTitle());
            noteDb.setStatus(note.isStatus());
            noteDb.setDate(note.getDate());

            noteRepository.saveAndFlush(noteDb);

        }
    }


    public void deleteNote(Long id){
        noteRepository.deleteById(id);
    }
}




















