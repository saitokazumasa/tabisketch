DO $$
BEGIN
    IF NOT EXISTS(SELECT * FROM PG_TYPE TYPE JOIN PG_ENUM ENUM ON TYPE.OID = ENUM.ENUMTYPID WHERE TYPE.TYPNAME = 'TRANSPORATIONS') THEN
        CREATE TYPE TRANSPORATIONS AS ENUM ('DRIVE', 'BICYCLE', 'WALK', 'TWO_WHEELER', 'TRANSIT');
    END IF;
END
$$;
