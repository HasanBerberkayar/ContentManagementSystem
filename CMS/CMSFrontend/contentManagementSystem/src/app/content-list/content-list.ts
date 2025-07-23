import { Component, OnInit, NgZone } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ContentService } from '../Services/ContentService';
import { CastService } from '../Services/CastService';
import { Content } from '../Models/Content';
import { ChangeDetectorRef } from '@angular/core';
import { Casts } from '../Models/Casts';
import './content-list.css';
import { lastValueFrom } from 'rxjs';
import { CastRequest } from '../Models/CastRequest';
import { Observable, forkJoin } from 'rxjs';
import { RouterModule, Router } from '@angular/router';
import { AuthService } from '../Services/AuthService';

@Component({
  selector: 'app-content-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './content-list.html',
  styleUrls: ['./content-list.css'],
})
export class ContentListComponent implements OnInit {
  contents: Content[] = [];
  selectedContent: Content | null = null;
  viewedContent: Content | null = null;
  isAddPopupOpen = false;

  newContent: Content = {
    metadata: {
      title: '',
      plot: '',
      poster: '',
      year: 0,
      language: '',
      country: '',
    },
    director: null,
    actors: [],
  };

  constructor(private contentService: ContentService,
    private cdr: ChangeDetectorRef,
    private castsService: CastService,
    private ngZone: NgZone,
    private authService:
      AuthService, private router: Router) { }

  ngOnInit(): void {
    this.loadContents();
    this.loadCasts();
  }

  loadContents(): void {
    this.contentService.getAllContents().subscribe((data: Content[]) => {
      this.contents = data;

      data.forEach(content => {
        const dir = content.director;
        if (dir && !this.directors.find(d => d.id === dir.id)) {
          this.directors.push(dir);
        }

        content.actors?.forEach(actor => {
          if (!this.actors.find(a => a.id === actor.id)) {
            this.actors.push(actor);
          }
        });
      });

      this.cdr.detectChanges();
    });
  }

  loadCasts(): void {
    this.castsService.getAllCasts().subscribe(data => {
      this.casts = data;
      this.directors = data.filter(cast => cast.role === 'director');
      this.actors = data.filter(cast => cast.role === 'actor');
      this.cdr.detectChanges();
    });
  }

  deleteContent(id: number): void {
    this.contentService.deleteContent(id).subscribe(() => {
      this.contents = this.contents.filter(content => content.id != id);
      this.ngZone.run(() => {
        this.cdr.detectChanges();
      });
    });
  }

  viewContent(content: Content): void {
    this.viewedContent = content;
  }

  openEditPopup(content: Content): void {
    this.selectedContent = { ...content, metadata: { ...content.metadata } };
    this.selectedActors = [...(content.actors || [])];
    this.selectedActorIds = this.selectedActors.map(a => a.id!).filter((id): id is number => id !== undefined);
    this.selectedDirectorId = content.director && content.director.id !== undefined ? content.director.id : null;
    this.isAddPopupOpen = false;
    this.viewedContent = null;
  }

  openAddPopup(): void {
    this.isAddPopupOpen = true;
  }

  closePopup(): void {
    this.directors = this.directors.filter(d => d.id! >= 0);
    this.actors = this.actors.filter(a => a.id! >= 0);
    this.selectedContent = null;
    this.isAddPopupOpen = false;
    this.viewedContent = null;

    this.newMetadata = {
      title: '',
      plot: '',
      poster: '',
      year: 0,
      language: '',
      country: ''
    };
    this.selectedDirectorId = null;
    this.selectedActorIds = [];
    this.selectedActors = [];
    this.selectedActorToAdd = null;

    this.ngZone.run(() => {
      this.cdr.detectChanges();
    });
  }

  saveEdit(): void {
    if (this.selectedContent) {
      const id = this.selectedContent.id!;
      const original = this.contents.find(c => c.id === id);
      if (!original) return;

      const newData = this.selectedContent.metadata;
      const oldData = original.metadata;

      const params: any = {};

      if (newData.title !== oldData.title) params.title = newData.title;
      if (newData.plot !== oldData.plot) params.plot = newData.plot;
      if (newData.poster !== oldData.poster) params.poster = newData.poster;
      if (newData.year !== oldData.year) params.year = newData.year;
      if (newData.language !== oldData.language) params.language = newData.language;
      if (newData.country !== oldData.country) params.country = newData.country;
      if (this.selectedDirectorId !== null && original.director !== null) {
        if (this.selectedDirectorId !== original.director.id) {
          params.directorId = this.selectedDirectorId;
        }
      }
      let newS: string = "";
      let oldS: string = "";
      for (const actor of this.selectedActors || []) {
        if (newS == "") {
          newS = actor.id?.toString() || "";
        } else {
          newS += "," + actor.id;
        }
      }
      for (const actor of original.actors || []) {
        if (oldS == "") {
          oldS = actor.id?.toString() || "";
        } else {
          oldS += "," + actor.id;
        }
      }
      if (newS !== oldS) {
        params.actorIds = newS;
      }
      if (Object.keys(params).length === 0) {
        alert('No changes detected.');
        return;
      }
      this.contentService.updateContent(id, params).subscribe(() => {
        this.loadContents();
        this.ngZone.run(() => {
          this.closePopup();
          this.cdr.detectChanges();
        });
      });
    }
  }

  getActorNames(actors: any[]): string {
    return actors?.map(a => a.name).join(', ') || '';
  }

  casts: Casts[] = [];
  directors: Casts[] = [];
  actors: Casts[] = [];

  newMetadata = {
    title: '',
    plot: '',
    poster: '',
    year: 0,
    language: '',
    country: ''
  };

  selectedDirectorId: number | null = null;
  selectedActorIds: number[] = [];
  selectedActors: Casts[] = [];
  selectedActorToAdd: Casts | null = null;


  addActor(actor: Casts): void {
    if (!this.selectedActorIds.includes(actor.id!)) {
      this.selectedActorIds.push(actor.id!);
      this.selectedActors.push(actor);
    }
  }


  removeActor(actorId: number): void {
    this.selectedActorIds = this.selectedActorIds.filter(id => id !== actorId);
    this.selectedActors = this.selectedActors.filter(actor => actor.id !== actorId);
  }


  submitContentRequest(): void {
    if (!this.selectedDirectorId) {
      alert("Please select a director.");
      return;
    }

    const castAddRequests: Observable<Casts>[] = [];

    if (this.selectedDirectorId < 0) {
      const directorName = this.directors.find(d => d.id === this.selectedDirectorId)?.name || '';
      const castRequest: CastRequest = {
        name: directorName,
        poster: '',
        role: 'director'
      };
      castAddRequests.push(this.castsService.addCast(castRequest));
    }

    this.selectedActors
      .filter(actor => actor.id !== undefined && actor.id < 0)
      .forEach(actor => {
        const castRequest: CastRequest = {
          name: actor.name,
          poster: actor.poster || '',
          role: 'actor'
        };
        castAddRequests.push(this.castsService.addCast(castRequest));
      });

    const handleContentSubmission = () => {
      const contentRequest = {
        metadata: this.newMetadata,
        directorId: this.selectedDirectorId,
        actorIds: this.selectedActorIds
      };

      this.contentService.addContent(contentRequest).subscribe(() => {
        this.loadContents();

        this.newMetadata = {
          title: '',
          plot: '',
          poster: '',
          year: 0,
          language: '',
          country: ''
        };
        this.selectedDirectorId = null;
        this.selectedActorIds = [];
        this.selectedActors = [];
        this.selectedActorToAdd = null;

        this.ngZone.run(() => {
          this.closePopup();
          this.cdr.detectChanges();
        });
      });
    };

    if (castAddRequests.length > 0) {
      forkJoin(castAddRequests).subscribe(newlyAddedCasts => {
        newlyAddedCasts.forEach(cast => {
          if (cast.role === 'director') {
            this.selectedDirectorId = cast.id!;
          } else {
            const index = this.selectedActorIds.findIndex(id => id < 0);
            if (index !== -1) {
              this.selectedActorIds[index] = cast.id!;
              this.selectedActors[index].id = cast.id!;
            }
          }
        });
        handleContentSubmission();
      });
    } else {
      handleContentSubmission();
    }
  }



  isLoading = false;

  private tempIdCounter = -1;

  generateTemporaryId(): number {
    return this.tempIdCounter--;
  }

  async searchInImdb(title: string) {
    if (!title || title.trim() === '') {
      alert("Please enter a valid title to search.");
      return;
    }

    this.isLoading = true;

    try {
      const imdbContent = await lastValueFrom(this.contentService.getContentFromImdb(title));

      if (!imdbContent.metadata) {
        console.error("Metadata is empty or undefined.");
        this.isLoading = false;
        return;
      }

      Object.assign(this.newMetadata, imdbContent.metadata);

      const directorFromImdb = imdbContent.director;
      if (directorFromImdb) {
        let foundDirector = this.directors.find(d => d.name.toLowerCase() === directorFromImdb.name.toLowerCase());
        if (!foundDirector) {
          const newDirector = { ...directorFromImdb, id: this.generateTemporaryId() };
          this.directors.push(newDirector);
          foundDirector = newDirector;
        }
        this.selectedDirectorId = foundDirector.id !== undefined ? foundDirector.id : null;
      }

      this.selectedActors = [];
      this.selectedActorIds = [];

      if (imdbContent.actors && imdbContent.actors.length > 0) {
        imdbContent.actors.forEach(actorFromImdb => {
          let foundActor = this.actors.find(a => a.name.toLowerCase() === actorFromImdb.name.toLowerCase());
          if (!foundActor) {
            const newActor = { ...actorFromImdb, id: this.generateTemporaryId() };
            this.actors.push(newActor);
            foundActor = newActor;
          }
          this.selectedActors.push(foundActor);
          this.selectedActorIds.push(foundActor.id!);
        });
      }

      this.isLoading = false;
      this.cdr.detectChanges();

    } catch (err) {
      this.isLoading = false;
      this.cdr.detectChanges();
      console.error("IMDb data retrieval error:", err);
      alert("Could not retrieve data from imdb. Please try again.");
    }
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/auth']);
  }
}