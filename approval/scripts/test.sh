


curl -v -H "Content-Type: application/json" -H "Accept: application/json" -X POST http://localhost:8080/approval/entity -d '{"entityId":"123", "emails": ["ian.li@hightail.com", "tracy.wang@hightail.com"]}' 

curl -H "Content-Type: application/json" -H "Accept: application/json" -X GET http://localhost:8080/approval/entity/123 && echo "" 
   
curl -v -H "Content-Type: application/json" -H "Accept: application/json" -X POST http://localhost:8080/approval/request -d '{"entityId":"123", "proposal": "granting access to yana.getin@hightail.com "}' 
  
export id=  
curl -v -H "Content-Type: application/json" -H "Accept: application/json" -X POST http://localhost:8080/approval/request/${id} \
-d '{"entityId":"123", "approver": "ian.li@hightail.com", "approved": "true", "comment" : "ok!!"}' 
  
curl -H "Content-Type: application/json" -H "Accept: application/json" -X GET http://localhost:8080/approval/request/${id}  && echo ""
