## Requirements
&emsp;The system is designed for a diverse group of users, each possessing specific objectives:  
the owner of the company - needs a clear overview of the ongoing and completed projects, to be able to manage all work and resources;
the existing customers - want to access the real-time progress of their order;
the potential customers - are expecting to find information about the company's services.
### Functional Requirements
1. As the owner of the company, I want to keep track of the new projects (of the following types: Residential, Commercial, Industrial or Road Construction), assign them a start date and have default values for some of the new project’s fields: expected duration - specific for each type and for residential projects: number of kitchens (1), bathrooms (1), other rooms with plumbing (1), if the project is a new build or a renovation (new build); for commercial projects: number of floors (1); for road construction: number of bridges (0), tunnels (0) and other environmental challenges (none), so I don’t have to enter all data every time I add a new project.

2. As the owner of the company I want to modify the project’s details(for residential projects: number of kitchens/bathrooms/other rooms with plumbing, size of the building; for commercial projects: the size, the use of the building and the number of floors; for industrial projects: the size and the use of the building; for road construction: the length and width of the road, number of bridges and tunnels, the environmental challenges) in case I accidentally type wrong values or the circumstances of the construction change.

3. As the owner of the company, I want to update the estimated expenses and resources in my project (expected duration, budget, expected expenses, estimated total hours), so I can get an overview of resources distribution for the whole project.

4. As the owner of the company, I want to update the materials expenses, salary expenses and man-hours used, using daily values, add notes about each value and get the system to calculate the total expenses for an ongoing project, so I don’t have to calculate the expenses myself and to remember what expenses I lastly added.

5. As the owner of the company, I want to see the expected duration, expected total hours, expected expenses, man-hours used, and total expenses for an ongoing project, so I can decide if the project is behind schedule.

6. As the owner of the company, I want to mark a project as behind the schedule, so that I can see the status of the project without checking all fields.

7. As the owner of the company, I want to assign an ID to each new project, so that each project will be uniquely represented and easier to find.

8. As the owner of the company, I want to delete a project, in case I accidentally create it or if it was completed a long time ago.

9. As the owner of the company, I want to store data from a completed project, then use it to calculate the average time and expenses for similar projects, so that I can compare and better estimate the resources for similar future projects.

10. As the owner of the company, I want to mark an ongoing project as completed, so I can use it for advertising and for future estimations.

11. As the owner of the company, I want to mark a project as ongoing, if I just created the project or if I accidentally marked it as completed. 

12. As the owner of the company, I want to search a project by ID and filter the projects by type, budget, start date, duration or status, to see or update project’s data.

13. As the owner of the company, I want to sort the projects by ID (alphabetically), type (alphabetically), status (alphabetically), budget (numerically), duration (numerically) or start date (chronologically) , so I can easily see the projects, accordingly to the criteria I’m interested in.

14. As the owner of the company, I want to see how many ongoing or completed projects I have, so I know the total number of projects and the number of projects that match the search criteria.

15. As the owner of the company, I want a website to advertise my construction company.

16. As a customer, I want to see the company's ongoing projects to see the progress of my order or how well the company works.

17. As a customer, I want to see the company’s available types of projects and some completed projects to get a better idea of what the company offers and delivers.

18. As a customer, I want to see the company's contact information, so I can contact the company if I want to hire it.  
### Non-Functional Requirements 
19. The system must use files as a backup strategy and every update in the system includes writing to a file.

