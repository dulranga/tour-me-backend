# API Endpoints Documentation

This document outlines the REST API endpoints required for the TourMe system, based on the UML documentation.

## 1. Authentication & Profile Management

| Method | Endpoint                 | Description                          | Actor   |
| :----- | :----------------------- | :----------------------------------- | :------ |
| POST   | `/api/auth/register`     | Register a new user (Tourist/Driver) | T, D    |
| POST   | `/api/auth/login`        | Authenticate user and return token   | T, D, A |
| POST   | `/api/auth/logout`       | Invalidate user session              | T, D, A |
| GET    | `/api/profile`           | Get current user profile details     | T, D, A |
| PUT    | `/api/profile`           | Update profile information           | T, D, A |
| POST   | `/api/profile/documents` | Upload verification documents        | D       |
| GET    | `/api/profile/documents` | View uploaded documents              | D, A    |

## 2. Itinerary Management

| Method | Endpoint                       | Description                                            | Actor   |
| :----- | :----------------------------- | :----------------------------------------------------- | :------ |
| POST   | `/api/itineraries`             | Create a new itinerary draft                           | T       |
| GET    | `/api/itineraries`             | List user's itineraries or all available (for drivers) | T, D    |
| GET    | `/api/itineraries/{id}`        | Get specific itinerary details                         | T, D, A |
| PUT    | `/api/itineraries/{id}`        | Update itinerary details                               | T       |
| POST   | `/api/itineraries/{id}/submit` | Submit itinerary for bids                              | T       |
| DELETE | `/api/itineraries/{id}`        | Cancel/Delete itinerary                                | T       |
| GET    | `/api/itineraries/search`      | Search/Filter available itineraries                    | D       |

## 3. Bidding System

| Method | Endpoint                     | Description                               | Actor   |
| :----- | :--------------------------- | :---------------------------------------- | :------ |
| POST   | `/api/itineraries/{id}/bids` | Submit a bid for an itinerary             | D       |
| GET    | `/api/itineraries/{id}/bids` | View all bids for a specific itinerary    | T, A    |
| GET    | `/api/bids`                  | View driver's own submitted bids          | D       |
| GET    | `/api/bids/{id}`             | Get bid details                           | T, D, A |
| POST   | `/api/bids/{id}/select`      | Tourist selects a specific bid            | T       |
| POST   | `/api/bids/{id}/accept`      | Driver confirms/accepts the selected trip | D       |
| POST   | `/api/bids/{id}/decline`     | Driver declines the selected trip         | D       |

## 4. Trip & Communication

| Method | Endpoint                   | Description                      | Actor |
| :----- | :------------------------- | :------------------------------- | :---- |
| GET    | `/api/trips`               | List active/past trips           | T, D  |
| GET    | `/api/trips/{id}/status`   | Get real-time status of a trip   | T, D  |
| POST   | `/api/trips/{id}/messages` | Send message to trip partner     | T, D  |
| GET    | `/api/trips/{id}/messages` | Get message history              | T, D  |
| PUT    | `/api/trips/{id}/location` | Update driver's current location | D     |

## 5. Reviews & Feedback

| Method | Endpoint                    | Description                            | Actor   |
| :----- | :-------------------------- | :------------------------------------- | :------ |
| POST   | `/api/trips/{id}/reviews`   | Rate and provide feedback for a driver | T       |
| GET    | `/api/drivers/{id}/reviews` | View ratings/feedback for a driver     | T, D, A |
| DELETE | `/api/reviews/{id}`         | Moderate/Remove feedback               | A       |

## 6. Administration

| Method | Endpoint                           | Description                        | Actor |
| :----- | :--------------------------------- | :--------------------------------- | :---- |
| GET    | `/api/admin/drivers/pending`       | List drivers awaiting verification | A     |
| POST   | `/api/admin/drivers/{id}/verify`   | Approve/Reject driver account      | A     |
| GET    | `/api/admin/users`                 | List all system users              | A     |
| PUT    | `/api/admin/users/{id}/suspend`    | Ban or suspend a user account      | A     |
| GET    | `/api/admin/system/activity`       | Monitor system-wide logs/activity  | A     |
| POST   | `/api/admin/disputes/{id}/resolve` | Resolve a trip dispute             | A     |

**Legend:**

- **T**: Tourist
- **D**: Driver
- **A**: Administrator
