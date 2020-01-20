DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `eliminarPlayerClassId`()
    NO SQL
DELETE FROM bfplayer WHERE class_id<3$$
DELIMITER ;