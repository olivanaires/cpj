-- update contracts set signature_end_date=end_date;
-- update files set contract_id=(select co.id from contracts co where co.file_id=files.id)
-- update contracts set payment_signature_value=payment_value;
alter table contracts alter column end_date drop not null;