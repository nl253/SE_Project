Use Case Descriptions
---------------------

**Use case name**

create annual review record

**Participating actors**

- repository
- reviewer (either manager or director)

**Normal flow**

1. load previews annual review record 
2. review previous year's achievements and outcomes
3. review previous year's tranining and mentoring and outcomes
4. after discussion, make note of future objectives and plans
5. after discussion, make note of future opportunities for mentoring and traning
6. write a recommendation, select type of recommendation from: promotion, dismissal, probation, remain in the current position. Optionally add detail.
7. write a summary
8. add employees comments
9. optionally select date (by default selects today)
10. confirm (sign off)

**Alternative flow**

          ``-||-``

11. create probation record

          ``-||-``

11. create termination record

          ``-||-``

11. create promotion record

**Pre-condition**

- due to have an annual review

**Post-condition**


-----------------------------------------------------

**Use case name**

get approval

**Participating actors**

- director (or director of HR)
- operational staff

**Normal flow**

1. try to access a document that you are not authorized to see
2. click ``request access``
3. fill in the approval form
4. justify request (provide description)
4. confirm and submit (the request will be submitted to the relevant person)

**Alternative flow**

**Pre-condition**

- trying to access docuementation 
- not having required access rights

**Post-condition**

- approval granted

-------------------------------------------------------------

**Use case name**

create record

**Participating actors**

**Normal flow**

**Alternative flow**

**Pre-condition**

**Post-condition**

-------------------------------------------------------------

**Use case name**

modify record

**Participating actors**

**Normal flow**

**Alternative flow**

**Pre-condition**

**Post-condition**

.. vim:ft=rst:
