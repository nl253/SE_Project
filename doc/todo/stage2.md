# Stage 2 

## Meeting Outline

- The priority of this stage is the planning documents.
- We are not expected to code at this stage
- [ ] Develop plans for how we will organise (see "Planning" below)
- [ ] We need to think about the structure of the app 
  + start working on the class diagram 
  + possibly split it into models, controller and the rest (see below)
- [ ] We need to review the use case diagrams they gave us and correct them (do we need scenarios again?)
- [ ] Decide what is the most important (ie what to tackle first - see Scrum below) 

## Scrum

<img src="https://cdn-8a82.kxcdn.com/wp-content/uploads/2017/02/scrum_process_afa_5000.jpg"></img>

- [ ] **Scrum Master** (?)
  + **The Scrum Master** is responsible for making sure a team lives by the values and practices of Scrum. 
  + The ScrumMaster is often considered a coach for the team, helping the team do the best work it possibly can.
- [ ] **Product Owner** (?)
  + **Product owner** is typically a project's key stakeholder. 
  + Part of the product owner responsibilities is to have a vision of what he or she wishes to build, and convey that vision to the scrum team.
- [ ] We need a product backlog 
  + identify all features (each 5 - 15h roughly)
  + put them in a table 
  + add ratings of effort 
  + add ratings of the return of time investment
- [ ] We need to plan the first iteration (aka sprint in Scrum)
  + decide what features from the product backlog go into this particular sprint
  + look into the product backlog for features with higest time investment return
  + use a system (eg Trello - see <a href="https://trello.com/b/ZEUhMEQL/software-engineering-project">an example I created</a>) to split the features amongst ourselves 
	* this will let us assign names to each task and keep track of what has been completed and what is left to do
  + construct a **sprint backlog** for the first sprint (Trello?) 

## Planning (name of each document, describe processes involved, purpose of each document)

### Project management

- Meetings once a week (with notes summarising what was discussed)
- Agile (Scrum)
- Workshops
- Trello to split tasks during each sprint (?) 
- FaceBook to stay in touch / deal with immediate issues
- For any documents to be completed by more than one person we can use Google Docs
- GANTT chart - do we really need it? (might be time consuming)

### QA

### Reviews

- During meetings

### Version Control

- Git(lab)
- Once we will agree on the class structure I will write + upload it

### Software Tests

- JUnit5 (API is essentially the same as JUnit4)
- I've already set everything up
- Bug tracking system on GitLab

## Structure

<img src="https://koenig-media.raywenderlich.com/uploads/2013/07/mvc0.png"></img>

- Reviewer interface ?
- Manager is a generalization of an Employee
- so is Director
- Many records - how about an abstract class from which they all inherit
- Many recommendations - how about an abstract class from which they all inherit
