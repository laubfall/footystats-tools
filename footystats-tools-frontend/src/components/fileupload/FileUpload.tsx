import React, { useRef } from "react";
import { FootyStatsCsvUploadControllerApi } from "../../footystats-frontendapi";
import { useFileUpload } from "../../react-use-file-upload/useFileUpload";
import { Button, Card, Col, Row } from "react-bootstrap";
import { Messages } from "../alert/Messages";
import translate from "../../i18n/translate";

export const FileUpload = () => {
	const {
		files,
		fileNames,
		fileTypes,
		totalSize,
		totalSizeInBytes,
		handleDragDropEvent,
		clearAllFiles,
		createFormData,
		setFiles,
		removeFile,
	} = useFileUpload();

	const inputRef = useRef<HTMLInputElement>();

	const [messages, setMessages] = React.useState<string[]>([]);

	const handleSubmit = async (e) => {
		e.preventDefault();

		const formData = createFormData();
		const files: Blob[] = [];

		formData.forEach((val) => files.push(val as File));

		new FootyStatsCsvUploadControllerApi()
			.uploadMultipleFiles({
				files,
			})
			.then((response) => {
				setMessages([translate("renderer.fileupload.upload.success")]);
			})
			.catch((error) => {
				setMessages([translate("renderer.fileupload.upload.failed")]);
				console.error("Failed to submit files.", error);
			});
	};

	return (
		<>
			<Messages messages={messages} />
			<Row>
				<h1>{translate("renderer.fileupload.title")}</h1>

				<p>{translate("renderer.fileupload.info")}</p>

				<div className="form-container">
					{/* Display the files to be uploaded */}
					<div>
						<ul>
							{fileNames.map((name) => (
								<li key={name}>
									<span>{name}</span>

									<span onClick={() => removeFile(name)}>
										<i className="fa fa-times" />
									</span>
								</li>
							))}
						</ul>

						{files.length > 0 && (
							<ul>
								<li>
									File types found: {fileTypes.join(", ")}
								</li>
								<li>Total Size: {totalSize}</li>
								<li>Total Bytes: {totalSizeInBytes}</li>

								<li className="clear-all">
									<Button onClick={() => clearAllFiles()}>
										Clear All
									</Button>
								</li>
							</ul>
						)}
					</div>

					{/* Provide a drop zone and an alternative button inside it to upload files. */}
					<Card
						body
						onDragEnter={handleDragDropEvent}
						onDragOver={handleDragDropEvent}
						onDrop={(e) => {
							handleDragDropEvent(e);
							setFiles(e, "a");
						}}
					>
						<p>{translate("renderer.fileupload.draghere")}</p>

						<Button onClick={() => inputRef.current?.click()}>
							{translate("renderer.fileupload.button.selectfile")}
						</Button>

						{/* Hide the crappy looking default HTML input */}
						<input
							ref={inputRef}
							type="file"
							multiple
							style={{ display: "none" }}
							onChange={(e) => {
								setFiles(e, "a");
								inputRef.current.value = null;
							}}
						/>

						<Button className={"m-lg-2"} onClick={handleSubmit}>
							{translate("renderer.fileupload.button.upload")}
						</Button>
					</Card>
				</div>
			</Row>
		</>
	);
};
